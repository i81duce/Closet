package com.example.services;//Created by KevinBozic on 3/10/16.

import com.example.entities.ClosetContents;
import com.example.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClosetContentsRepository extends CrudRepository<ClosetContents, Integer> {
    List<ClosetContents> findByUser(User user);
}
