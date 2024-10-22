package com.example.user.Repository;

import com.example.user.Entity.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface UserRepository extends GenericRepository<User, Long> {
}

