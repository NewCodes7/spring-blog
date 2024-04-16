package me.newcodes.blog.dto;

import me.newcodes.blog.domain.Article;

public class ArticleResponse {

    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}