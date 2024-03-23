package com.fitness.courses.http.massenger.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestMessageServiceImpl implements RestMessageService
{
    private final MessageService messageService;

    @Autowired
    public RestMessageServiceImpl(MessageService messageService)
    {
        this.messageService = messageService;
    }
}
