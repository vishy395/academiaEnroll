package com.ssn.academiaEnroll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class StaticResourceController {

    @GetMapping("/uploads/**")
    public RedirectView handleUploads() {
        return new RedirectView("/uploads/");
    }
}