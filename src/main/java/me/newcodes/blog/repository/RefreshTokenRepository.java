package me.newcodes.blog.repository;

import java.util.Optional;
import me.newcodes.blog.domain.RefreshToken;

public interface RefreshTokenRepository {
    Optional<RefreshToken> findByUserId(Long userId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    RefreshToken save(RefreshToken refreshToken);
}
