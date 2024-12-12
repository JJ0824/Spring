package com.dw.jdbcApp.service;

import com.dw.jdbcApp.repository.RepositoryPractice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicePractice {
    @Autowired
    RepositoryPractice repositoryPractice;

    public String practice() {
        return repositoryPractice.practice();
    }
}
