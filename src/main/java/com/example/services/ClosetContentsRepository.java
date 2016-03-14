package com.example.services;//Created by KevinBozic on 3/10/16.

import com.example.entities.ClosetContents;
import com.example.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClosetContentsRepository extends PagingAndSortingRepository<ClosetContents, Integer> {
    Page<ClosetContents> findByUser(User user, Pageable pageable);
}
