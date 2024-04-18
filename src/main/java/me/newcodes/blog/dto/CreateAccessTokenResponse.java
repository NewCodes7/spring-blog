package me.newcodes.blog.dto;

public class CreateAccessTokenResponse {
    private String accessToken;

    public CreateAccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
