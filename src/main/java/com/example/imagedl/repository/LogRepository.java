package com.example.imagedl.repository;

import com.example.imagedl.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {

    List<Log> findByRequest(String request);
    Log findById(long id);
}
