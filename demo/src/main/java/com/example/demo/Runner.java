package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class Runner implements ApplicationRunner {
    private final WebClient webClient;

    record GenReq(String name, String regNo, String email) {}
    record GenRes(String webhook, String accessToken) {}
    record SubmitReq(String finalQuery) {}

    @Override
    public void run(ApplicationArguments args) {
        // Step 1: Generate webhook
        GenRes gen = webClient.post()
                .uri("https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new GenReq("John Doe", "REG12347", "john@example.com"))
                .retrieve()
                .bodyToMono(GenRes.class)
                .block();

        System.out.println("Webhook: " + gen.webhook());
        System.out.println("Token: " + gen.accessToken());

        // Step 2: Decide question by regNo last-2 (FIXED)
        String regNo = "REG12347";
        String digits = regNo.replaceAll("\\D", ""); // "12347"
        int last2 = Integer.parseInt(digits.substring(digits.length() - 2)); // "47" -> 47
        boolean isOdd = (last2 % 2) == 1;

        System.out.println("Digits extracted: " + digits);
        System.out.println("Last 2 digits: " + last2);
        System.out.println("Is odd (Question 1): " + isOdd);

        // Step 3: Build final SQL - ACTUAL QUERY ADDED
        String finalQuery = """
            SELECT 
                p.AMOUNT as SALARY,
                CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) as NAME,
                TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) as AGE,
                d.DEPARTMENT_NAME
            FROM PAYMENTS p
            JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID
            JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
            WHERE DAY(p.PAYMENT_TIME) != 1
            ORDER BY p.AMOUNT DESC
            LIMIT 1
            """;

        // Step 4: Submit answer
        String resp = webClient.post()
                .uri(gen.webhook())
                .header("Authorization", gen.accessToken()) // Bearer nahi
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SubmitReq(finalQuery))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Submit response: " + resp);
    }
}