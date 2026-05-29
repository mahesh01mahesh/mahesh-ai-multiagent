package multiagent.service;

import org.springframework.stereotype.Service;

@Service
public class SummaryAgent {

    private final GeminiService geminiService;

    public SummaryAgent(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String summarize(String text) {
        String prompt = "Summarize the following text in 3-4 sentences:\n\n" + text;
        return geminiService.generateContent(prompt);
    }
}