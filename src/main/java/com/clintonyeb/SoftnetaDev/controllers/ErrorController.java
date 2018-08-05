package com.clintonyeb.SoftnetaDev.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ErrorController {
    @GetMapping("/error")
    public String error(Model model) {
        return "error";
    }
}
