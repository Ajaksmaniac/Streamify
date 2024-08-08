package com.ajaksmaniac.streamify.unit.util;

import com.ajaksmaniac.streamify.entity.RoleEntity;
import com.ajaksmaniac.streamify.entity.UserEntity;
import com.ajaksmaniac.streamify.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUtilTest {

    private UserUtil userUtil;

    @BeforeEach
    void setUp() {
        userUtil = new UserUtil();
    }

    @Test
    void testIsUserAdmin() {
        // Mock UserEntity and Role
        UserEntity user = mock(UserEntity.class);
        RoleEntity role = mock(RoleEntity.class);

        // Define behavior
        when(user.getRole()).thenReturn(role);
        when(role.getName()).thenReturn("admin");

        // Test method
        boolean isAdmin = userUtil.isUserAdmin(user);

        // Verify
        assertTrue(isAdmin);
    }

    @Test
    void testIsUserNotAdmin() {
        // Mock UserEntity and Role
        UserEntity user = mock(UserEntity.class);
        RoleEntity role = mock(RoleEntity.class);

        // Define behavior
        when(user.getRole()).thenReturn(role);
        when(role.getName()).thenReturn("user");

        // Test method
        boolean isAdmin = userUtil.isUserAdmin(user);

        // Verify
        assertFalse(isAdmin);
    }

    @Test
    void testIsUserContentCreator() {
        // Mock UserEntity and Role
        UserEntity user = mock(UserEntity.class);
        RoleEntity role = mock(RoleEntity.class);

        // Define behavior
        when(user.getRole()).thenReturn(role);
        when(role.getName()).thenReturn("content_creator");

        // Test method
        boolean isContentCreator = userUtil.isUserContentCreator(user);

        // Verify
        assertTrue(isContentCreator);
    }

    @Test
    void testIsUserNotContentCreator() {
        // Mock UserEntity and Role
        UserEntity user = mock(UserEntity.class);
        RoleEntity role = mock(RoleEntity.class);

        // Define behavior
        when(user.getRole()).thenReturn(role);
        when(role.getName()).thenReturn("user");

        // Test method
        boolean isContentCreator = userUtil.isUserContentCreator(user);

        // Verify
        assertFalse(isContentCreator);
    }
}
