package com.clintonyeb.SoftnetaDev.controllers;

import com.clintonyeb.SoftnetaDev.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;


}
