package me.newcodes.blog.repository;

import java.util.List;
import me.newcodes.blog.domain.Article;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository {
    Article save(Article article);
//    Optional<Member> findById(Long id);
//    Optional<Member> findByName(String name);
    List<Article> findAll();
}