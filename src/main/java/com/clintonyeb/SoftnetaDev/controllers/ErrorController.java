package com.clintonyeb.SoftnetaDev.controllers;

import com.clintonyeb.SoftnetaDev.Application;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/***
 * Handles errors in the application.
 */
public class ErrorController {

    /***
     * Handles generic errors
     */
    @GetMapping("/error")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String error(Model model) {
        Application.logger.info("Internal Error occurred.");
        model.addAttribute("message", "There was an error processing your request");
        return "error";
    }

    /***
     * Handles requests that do not match any route.
     */
    @GetMapping("/*")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(Model model) {
        Application.logger.info("No route matches specified route.");
        return "404";
    }

    /***
     * Handles requests that do not match any route.
     */
    @GetMapping("/**")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound_2(Model model) {
        return notFound(model);
    }
}
