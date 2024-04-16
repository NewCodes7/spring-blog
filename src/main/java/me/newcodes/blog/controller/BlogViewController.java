package me.newcodes.blog.controller;

import me.newcodes.blog.domain.Article;
import me.newcodes.blog.dto.ArticleListViewResponse;
import me.newcodes.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogViewController {

    private final BlogService blogService;

    @Autowired
    public BlogViewController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles);

        return "articleList";
    }

}