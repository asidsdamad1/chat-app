package org.asi.authservice.web.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.asi.authservice.jwt.JWTBuilder;
import org.asi.authservice.jwt.JWTUtils;
import org.asi.authservice.web.controller.payload.AuthRequest;
import org.asi.authservice.web.controller.payload.AuthResponse;
import org.asi.exceptionutils.UnauthenticatedException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication API spec for Swagger doc
 * Implemented and automatically overridden by Spring Security filters.
 * Request mapping value coming from JWTConfig class
 *
 * @see org.asi.authservice.jwt.JWTConfig
 */
@Tag(name = "Authorization", description = "Login endpoint")
@RequestMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class AuthorizationController {
    private final JWTBuilder jwtBuilder;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Login", description = "Generating JWT tokens", tags = {"Authorization"})
    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "JWT token",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AuthResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<String> fakeLogin(@Parameter(description = "Username and password",
            required = true, schema = @Schema(implementation = AuthRequest.class))
                          @RequestBody AuthRequest authRequestModel) {

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequestModel.getUsername(),
                            authRequestModel.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new UnauthenticatedException("Incorrect username or password");
        }

        return ResponseEntity.ok(jwtBuilder.buildToken(authentication));
    }
}
