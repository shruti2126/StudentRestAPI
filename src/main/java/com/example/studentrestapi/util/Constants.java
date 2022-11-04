package com.example.studentrestapi.util;

import com.example.studentrestapi.dao.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Constants {
    private Map<String, String> messages;

    @Autowired
    StudentRepository studentRepo;

    public String getMessage(String msgName) {
        return messages.get(msgName);
    }
}