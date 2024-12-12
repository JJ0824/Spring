package com.dw.secondapp.repository;

import org.springframework.stereotype.Repository;

@Repository
public class CallPiRepository {
    public String Pi() {
        return "From repository: " + Math.PI;
    }
}
