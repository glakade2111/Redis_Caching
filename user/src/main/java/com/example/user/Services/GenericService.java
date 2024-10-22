package com.example.user.Services;


import com.example.user.Repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
@Service
public class GenericService<T, ID extends Serializable> {

    @Autowired
    private GenericRepository<T, ID> repository;

    public List<T> findAll() {

        return repository.findAll();
    }

    public Optional<T> findById(ID id) {

        return repository.findById(id);
    }

    public T save(T entity) {

        return repository.save(entity);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}