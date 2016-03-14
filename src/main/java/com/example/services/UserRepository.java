package com.example.services;//Created by KevinBozic on 3/10/16.

import com.example.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByName(String username);
}
