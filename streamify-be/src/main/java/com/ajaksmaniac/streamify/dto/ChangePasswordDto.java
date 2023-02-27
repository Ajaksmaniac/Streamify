package com.ajaksmaniac.streamify.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@JsonSerialize
@Data
public class ChangePasswordDto {
    private String username;
    private String oldPassword;
    private String newPassword;
}
