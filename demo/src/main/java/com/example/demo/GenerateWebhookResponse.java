package com.example.demo;

public class GenerateWebhookResponse {
    private String webhook;
    private String accessToken;

    // Default constructor (required by WebClient)
    public GenerateWebhookResponse() {}

    public GenerateWebhookResponse(String webhook, String accessToken) {
        this.webhook = webhook;
        this.accessToken = accessToken;
    }

    // getters & setters
    public String getWebhook() { return webhook; }
    public String getAccessToken() { return accessToken; }
    public void setWebhook(String webhook) { this.webhook = webhook; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
}
