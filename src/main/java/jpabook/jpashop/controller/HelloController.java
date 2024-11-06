package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j //    Logger log= LoggerFactory.getLogger(HelloController.class); 와 동일
public class HelloController {

    @RequestMapping("/")
    public String home() {
        log.info("Home Controller");
        return "home";
    }
}
