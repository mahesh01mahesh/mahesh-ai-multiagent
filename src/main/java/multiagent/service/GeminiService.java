package multiagent.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    // ✅ Only reads from environment variable
    private final String API_KEY = System.getenv("GROQ_API_KEY");

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.groq.com")
            .build();

    public String generateContent(String prompt) {

        Map<String, Object> requestBody = Map.of(
            "model", "llama-3.1-8b-instant",
            "messages", List.of(
                Map.of(
                    "role", "user",
                    "content", prompt
                )
            )
        );

        try {
            return webClient.post()
                    .uri("/openai/v1/chat/completions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + API_KEY)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (WebClientResponseException e) {
            throw new RuntimeException("Groq API error: "
                + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }
}