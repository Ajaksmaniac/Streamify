package com.ajaksmaniac.streamify.service.implementation;

import com.ajaksmaniac.streamify.dto.RoleDto;
import com.ajaksmaniac.streamify.entity.RoleEntity;
import com.ajaksmaniac.streamify.exception.RoleAlreadyExistsException;
import com.ajaksmaniac.streamify.exception.RoleNotFoundException;
import com.ajaksmaniac.streamify.repository.RoleRepository;
import com.ajaksmaniac.streamify.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class RoleServiceImplementation implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleDto getRoleById(Long roleId) {

        if(!roleRepository.existsById(roleId)){
            throw new RoleNotFoundException();
        }
        RoleEntity en = roleRepository.getById(roleId);
        return new RoleDto(en.getId(), en.getName());
    }

    @Override
    public void createRole(RoleDto roleDto) {

        if(roleRepository.existsByName(roleDto.getName())){
            throw new RoleAlreadyExistsException();
        }

        roleRepository.save(new RoleEntity(null, roleDto.getName()));
    }

    @Override
    public void updateRole(RoleDto roleDto) {

        if(!roleRepository.existsById(roleDto.getId())){
            throw new RoleNotFoundException();
        }

        roleRepository.save(new RoleEntity(roleDto.getId(),roleDto.getName()));
    }

    @Override
    public void deleteById(Long id) {
        if(!roleRepository.existsById(id)){
            throw new RoleNotFoundException();
        }

        roleRepository.deleteById(id);
    }
}
