package multiagent.service;

import multiagent.model.ChatMessage;
import multiagent.repository.ChatRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatHistoryService {

    private final ChatRepository chatRepository;

    public ChatHistoryService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    // Save chat to database
    public ChatMessage saveChat(String agentType, String prompt, String response) {
        ChatMessage msg = new ChatMessage();
        msg.setAgentType(agentType);
        msg.setPrompt(prompt);
        msg.setResponse(response);
        return chatRepository.save(msg);
    }

    // Get last 10 chats
    public List<ChatMessage> getRecentChats() {
        return chatRepository.findTop10ByOrderByCreatedAtDesc();
    }

    // Get chats by agent type
    public List<ChatMessage> getChatsByAgent(String agentType) {
        return chatRepository.findByAgentTypeOrderByCreatedAtDesc(agentType);
    }
}