package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebhookService {

    private final WebClient webClient;

    public WebhookService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://bfhldevapigw.healthrx.co.in")
                .build();
    }

    public GenerateWebhookResponse generateWebhook() {
        GenerateWebhookRequest request = new GenerateWebhookRequest(
                "John Doe",
                "REG12347",
                "john@example.com"
        );

        return webClient.post()
                .uri("/hiring/generateWebhook/JAVA")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GenerateWebhookResponse.class)
                .block(); // blocking since it's a startup call
    }

    // NEW METHOD ADDED - Submit query method
    public String submitQuery(String webhookUrl, String accessToken, String finalQuery) {
        SubmitRequest request = new SubmitRequest(finalQuery);

        return webClient.post()
                .uri(webhookUrl)
                .header("Authorization", accessToken) // No "Bearer " prefix
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}