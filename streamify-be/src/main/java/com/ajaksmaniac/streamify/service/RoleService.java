package com.ajaksmaniac.streamify.service;

import com.ajaksmaniac.streamify.dto.RoleDto;

public interface RoleService {
    RoleDto getRoleById(Long roleId);
    void createRole(RoleDto roleDto);
    void updateRole(RoleDto roleDto);
    void deleteById(Long id);



}
