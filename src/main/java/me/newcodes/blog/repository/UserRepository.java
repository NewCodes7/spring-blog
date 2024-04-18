package me.newcodes.blog.repository;

import java.util.Optional;
import me.newcodes.blog.domain.Article;
import me.newcodes.blog.domain.User;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    User save(User user);
    Optional<User> findById(Long id);
}