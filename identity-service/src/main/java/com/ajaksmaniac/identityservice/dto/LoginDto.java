package com.ajaksmaniac.identityservice.dto;

import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerializableSchema
public class LoginDto {
    private String username;
    private String password;
}
