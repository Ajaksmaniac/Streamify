package com.ajaksmaniac.identityservice.dto;

import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonSerializableSchema
public class UserDto {

    Long id;
    String username;
    RoleDto role;

}
