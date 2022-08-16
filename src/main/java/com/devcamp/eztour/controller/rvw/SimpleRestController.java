package com.devcamp.eztour.controller.rvw;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SimpleRestController {

    @GetMapping("/test")
    public String test() {
        return "test"; //  뷰이름
    }
}
