package me.newcodes.blog.controller;

import me.newcodes.blog.domain.Article;
import me.newcodes.blog.dto.AddArticleRequest;
import me.newcodes.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlogApiController {
    private final BlogService blogService;

    @Autowired
    public BlogApiController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }
}