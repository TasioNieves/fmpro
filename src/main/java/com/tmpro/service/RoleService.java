package com.tmpro.service;

import com.tmpro.model.Role;
import com.tmpro.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;



    @Service
    public class RoleService {
        private final RoleRepository roleRepository;

        public RoleService(RoleRepository roleRepository) {
            this.roleRepository = roleRepository;
        }

        public List<Role> findAll() {
            return roleRepository.findAll();
        }

    }


