package com.example.user.Controller;

import com.example.user.Entity.User;
import com.example.user.Services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private GenericService<User, Long> service;

    @GetMapping
    public List<User> getAll() {
        return service.findAll();
    }

    @GetMapping("/User/{id}")
    @Cacheable(value = "User", key = "#id", unless = "#result == null")  // Cache only User, not ResponseEntity
    public User getById(@PathVariable Long id) {
        return service.findById(id).orElse(null);  // Return User directly for caching
    }

    @PostMapping
    public User create(@RequestBody User entity) {
        return service.save(entity);
    }

    @PutMapping("/User/{id}")
    @CachePut(value = "User", key = "#id", unless = "#result == null")  // Cache only User after update
    public User update(@PathVariable Long id, @RequestBody User entity) {
        return service.findById(id)
                .map(existingEntity -> service.save(entity))
                .orElse(null);
    }

    @DeleteMapping("/User/{id}")
    @CacheEvict(value = "User", key = "#id", beforeInvocation = true)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Wrapping User result in ResponseEntity in all response methods

    @GetMapping("/User/response/{id}")
    public ResponseEntity<User> getByIdResponse(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/User/response/{id}")
    public ResponseEntity<User> updateResponse(@PathVariable Long id, @RequestBody User entity) {
        return service.findById(id)
                .map(existingEntity -> {
                    service.save(entity);
                    return ResponseEntity.ok(entity);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
