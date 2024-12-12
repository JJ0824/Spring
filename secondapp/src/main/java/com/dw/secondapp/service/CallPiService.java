package com.dw.secondapp.service;

import com.dw.secondapp.repository.CallPiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallPiService {
    @Autowired
    CallPiRepository callPiRepository;

    public String Pi() {
        if (callPiRepository.Pi()==null){
            return "From service: "+ Math.PI;
        } else {
            return callPiRepository.Pi();
        }
    }
}
