package me.newcodes.blog.dto;

import me.newcodes.blog.domain.Article;

public class AddArticleRequest {
    private String title;
    private String content;

    public AddArticleRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Article toEntity() {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);

        return article;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
