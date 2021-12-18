package com.yunhalee.withEmployee.message.domain;

import com.yunhalee.withEmployee.conversation.domain.Conversation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    List<Message> findAllByOrderByCreatedAtDesc();

    List<Message> findByConversationOrderByCreatedAtDesc(Conversation conversation);

}
