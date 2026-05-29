package multiagent.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import multiagent.dto.PromptRequest;
import multiagent.model.ChatMessage;
import multiagent.service.ChatHistoryService;
import multiagent.service.QuestionAnswerAgent;
import multiagent.service.SummaryAgent;
import multiagent.service.TranslatorAgent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class HelloController {

    private final SummaryAgent summaryAgent;
    private final TranslatorAgent translatorAgent;
    private final QuestionAnswerAgent questionAnswerAgent;
    private final ChatHistoryService chatHistoryService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public HelloController(SummaryAgent summaryAgent,
                           TranslatorAgent translatorAgent,
                           QuestionAnswerAgent questionAnswerAgent,
                           ChatHistoryService chatHistoryService) {
        this.summaryAgent = summaryAgent;
        this.translatorAgent = translatorAgent;
        this.questionAnswerAgent = questionAnswerAgent;
        this.chatHistoryService = chatHistoryService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody PromptRequest request) {
        try {
            String rawResult;

            switch (request.getAgentType().toLowerCase()) {
                case "summary":
                    rawResult = summaryAgent.summarize(request.getPrompt());
                    break;
                case "translate":
                    rawResult = translatorAgent.translate(
                        request.getPrompt(),
                        request.getTargetLanguage()
                    );
                    break;
                case "qa":
                default:
                    rawResult = questionAnswerAgent.answer(request.getPrompt());
                    break;
            }

            // Extract answer text
            JsonNode root = objectMapper.readTree(rawResult);
            String answer = root
                .path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();

            // ✅ Save to database
            chatHistoryService.saveChat(
                request.getAgentType(),
                request.getPrompt(),
                answer
            );

            return ResponseEntity.ok(answer);

        } catch (Exception e) {
            return ResponseEntity
                .status(500)
                .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    // ✅ Get recent chat history
    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getHistory() {
        return ResponseEntity.ok(chatHistoryService.getRecentChats());
    }

    // ✅ Get chat history by agent
    @GetMapping("/history/{agentType}")
    public ResponseEntity<List<ChatMessage>> getHistoryByAgent(
            @PathVariable String agentType) {
        return ResponseEntity.ok(chatHistoryService.getChatsByAgent(agentType));
    }
}