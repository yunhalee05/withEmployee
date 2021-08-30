package com.yunhalee.withEmployee.Repository;

import com.yunhalee.withEmployee.entity.Conversation;
import com.yunhalee.withEmployee.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    List<Message> findAllByOrderByCreatedAtDesc();

    List<Message> findByConversationOrderByCreatedAtDesc(Conversation conversation);

}
