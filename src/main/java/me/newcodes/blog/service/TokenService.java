package me.newcodes.blog.service;

import java.time.Duration;
import me.newcodes.blog.config.jwt.TokenProvider;
import me.newcodes.blog.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    @Autowired
    public TokenService(TokenProvider tokenProvider, RefreshTokenService refreshTokenService, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
