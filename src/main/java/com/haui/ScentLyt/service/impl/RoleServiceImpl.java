package com.haui.ScentLyt.service.impl;

import com.haui.ScentLyt.entity.Role;
import com.haui.ScentLyt.repository.RoleRepository;
import com.haui.ScentLyt.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAllByActive(true);
    }
}
