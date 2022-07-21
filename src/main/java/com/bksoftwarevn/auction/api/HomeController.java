package com.bksoftwarevn.auction.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping(value = "/check",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public String home() {
        return "Application running.....!";
    }
}
