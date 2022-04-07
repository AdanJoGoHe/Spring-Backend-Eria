package com.eria.EriaWebBackend.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class FileController {

  @GetMapping("/api/image")
  public String index() {
    return "Greetings from Spring Boot!";
  }

  @PostMapping(path = "/api/image")
  public String receiveImage(byte[] image){
    log.error("Soy el post de imagen");
    return "Im the shadow Behind";
  }

}
