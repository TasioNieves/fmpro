package com.tmpro.controller;

import com.tmpro.model.Role;
import com.tmpro.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



    @RestController
    @RequestMapping("/roles")
    public class RoleController {

        private final RoleService roleService;

        public RoleController(RoleService roleService) {
            this.roleService = roleService;
        }

        @GetMapping("/roles")
        public List<Role> getAllRoles() {
            return roleService.findAll();
        }
    }



