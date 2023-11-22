package com.example.demo.service;

import com.example.demo.dto.RoleDto;
import com.example.demo.entity.Role;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role create(RoleDto role) {
        log.info("Saving new role {} to database", role.getName());
        final Role roleMapped = modelMapper.map(role, Role.class);
        return roleRepository.save(roleMapped);
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new NotFoundException(name));
    }
}
