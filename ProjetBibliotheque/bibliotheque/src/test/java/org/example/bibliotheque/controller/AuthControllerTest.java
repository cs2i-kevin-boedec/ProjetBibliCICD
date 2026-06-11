package org.example.bibliotheque.controller;

import org.example.bibliotheque.dto.auth.ChangePasswordRequest;
import org.example.bibliotheque.dto.auth.LoginRequest;
import org.example.bibliotheque.dto.auth.LoginResponse;
import org.example.bibliotheque.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_shouldCallAuthServiceAndReturnResponse() {
        // Arrange
        LoginRequest request = mock(LoginRequest.class);
        LoginResponse expectedResponse = mock(LoginResponse.class);

        when(authService.login(request)).thenReturn(expectedResponse);

        // Act
        LoginResponse actualResponse = authController.login(request);

        // Assert
        assertSame(expectedResponse, actualResponse);
        verify(authService, times(1)).login(request);
        verifyNoMoreInteractions(authService);
    }

    @Test
    void changePassword_shouldCallAuthService() {
        // Arrange
        ChangePasswordRequest request = mock(ChangePasswordRequest.class);

        // Act
        authController.changePassword(request);

        // Assert
        verify(authService, times(1)).changePassword(request);
        verifyNoMoreInteractions(authService);
    }
}