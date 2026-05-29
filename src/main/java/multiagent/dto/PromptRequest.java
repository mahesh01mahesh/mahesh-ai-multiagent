package multiagent.dto;

public class PromptRequest {

    private String prompt;
    private String agentType;   // "summary", "translate", "qa"
    private String targetLanguage; // only for translate

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public String getAgentType() { return agentType; }
    public void setAgentType(String agentType) { this.agentType = agentType; }

    public String getTargetLanguage() { return targetLanguage; }
    public void setTargetLanguage(String targetLanguage) { this.targetLanguage = targetLanguage; }
}