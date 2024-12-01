package com.tmpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;




    @Controller
    public class AngularController {
        @GetMapping(value = "/{path:[^\\.]*}") // Coincide con cualquier ruta sin extensión de archivo
        public String redirect() {
            return "forward:/index.html"; // Devuelve el index.html
        }
    }



