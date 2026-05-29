package multiagent.repository;

import multiagent.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    // Get all chats by agent type
    List<ChatMessage> findByAgentTypeOrderByCreatedAtDesc(String agentType);

    // Get last 10 chats
    List<ChatMessage> findTop10ByOrderByCreatedAtDesc();
}