package com.clintonyeb.SoftnetaDev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FeedController {

    @Autowired
    private FeedRepository feedRepository;

    @GetMapping("/")
    public String home(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "feeds";
    }

    @GetMapping("/feeds")
    public String feeds(@RequestParam(name="size", required=false, defaultValue="20") String size,
                                             @RequestParam(name="page", required=false, defaultValue="1") String page,
                                             Model model) {
        model.addAttribute("items", feedRepository.findAll());
        return "feeds";
    }
}
