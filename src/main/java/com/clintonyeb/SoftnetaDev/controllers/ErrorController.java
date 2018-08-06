package com.clintonyeb.SoftnetaDev.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ErrorController {

    @GetMapping("/error")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String error(Model model) {
        model.addAttribute("message", "There was an error processing your request");
        return "error";
    }

    @GetMapping("/*")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(Model model) {
        return "404";
    }

    @GetMapping("/**")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound_2(Model model) {
        return "404";
    }
}
