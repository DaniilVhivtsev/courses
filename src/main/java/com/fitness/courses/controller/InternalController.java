package com.fitness.courses.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.global.Languages;

@RestController
@RequestMapping("/internal")
public class InternalController
{
    @GetMapping("/languages")
    public ResponseEntity<Languages> getPlatformLanguages()
    {
        return null;
    }
}
