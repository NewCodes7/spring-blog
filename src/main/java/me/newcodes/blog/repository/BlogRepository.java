package me.newcodes.blog.repository;

import java.util.List;
import java.util.Optional;
import me.newcodes.blog.domain.Article;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository {
    Article save(Article article);
    List<Article> findAll();
    Optional<Article> findById(Long id);
    void deleteById(Long id);
}