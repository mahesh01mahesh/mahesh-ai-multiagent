package multiagent.service;

import org.springframework.stereotype.Service;

@Service
public class QuestionAnswerAgent {

    private final GeminiService geminiService;

    public QuestionAnswerAgent(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String answer(String question) {
        String prompt = "Answer the following question clearly and concisely:\n\n" + question;
        return geminiService.generateContent(prompt);
    }
}