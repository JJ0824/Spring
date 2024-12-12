package com.dw.jdbcapp.service;

import com.dw.jdbcapp.repository.RepositoryPractice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicePractice {
    @Autowired
    RepositoryPractice repositoryPractice;

    public String practice() {
        if(repositoryPractice==null) {
            return "Service : 연습";
        } else {
            return repositoryPractice.practice();
        }
    }
}
