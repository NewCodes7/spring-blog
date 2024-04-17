package me.newcodes.blog.dto;

import me.newcodes.blog.domain.Article;

public class ArticleViewResponse {

    private Long id;
    private String title;
    private String content;

    public ArticleViewResponse() {
    }

    public ArticleViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}