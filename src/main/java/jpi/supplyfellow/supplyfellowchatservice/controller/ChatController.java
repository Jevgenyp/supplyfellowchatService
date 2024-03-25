package jpi.supplyfellow.supplyfellowchatservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


@RestController
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.engine-name}")
    private String engineName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody String userInput) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        String sanitizedInput = sanitizeUserInput(userInput);
        String requestBody = constructRequestBody(sanitizedInput);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                    "https://api.openai.com/v1/chat/completions",
                    requestEntity,
                    String.class);

            String content;
            try {
                content = extractMessageContent(responseEntity.getBody());
            } catch (JsonProcessingException e) {
                log.error("Error processing JSON response from OpenAI", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing response from OpenAI");
            }

            return ResponseEntity.ok(content);
        } catch (Exception e) {
            log.error("Error during the OpenAI API call", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error calling OpenAI API");
        }
    }

    @GetMapping("/chat")
    public ResponseEntity<String> getChat() {
        return ResponseEntity.ok("Chat service is running.");
    }

    private String sanitizeUserInput(String userInput) {
        return userInput.trim().replace("\"", "\\\"");
    }

    private String constructRequestBody(String userInput) {
        return String.format(
                "{\"model\": \"%s\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",
                engineName, userInput);
    }

    private String extractMessageContent(String responseBody) throws JsonProcessingException {
        JsonNode responseJson = objectMapper.readTree(responseBody);
        return responseJson.path("choices").get(0).path("message").path("content").asText();
    }

    private String createErrorResponse(Exception e) {
        return String.format("{\"error\": \"%s\"}", e.getMessage());
    }
}
