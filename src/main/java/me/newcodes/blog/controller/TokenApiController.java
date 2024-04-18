package me.newcodes.blog.controller;

import me.newcodes.blog.dto.CreateAccessTokenRequest;
import me.newcodes.blog.dto.CreateAccessTokenResponse;
import me.newcodes.blog.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenApiController {

    private final TokenService tokenService;

    @Autowired
    public TokenApiController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}