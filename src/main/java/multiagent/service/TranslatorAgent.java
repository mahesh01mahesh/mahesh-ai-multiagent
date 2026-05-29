package multiagent.service;

import org.springframework.stereotype.Service;

@Service
public class TranslatorAgent {

    private final GeminiService geminiService;

    public TranslatorAgent(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String translate(String text, String targetLanguage) {
        String prompt = "Translate the following text to " + targetLanguage + ":\n\n" + text;
        return geminiService.generateContent(prompt);
    }
}