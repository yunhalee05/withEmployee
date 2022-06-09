package com.yunhalee.withEmployee.conversation.domain;

import com.yunhalee.withEmployee.user.domain.User;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Integer> {


    @Query(value = "SELECT c FROM Conversation c LEFT JOIN FETCH c.users u LEFT JOIN FETCH u.companies p WHERE u.id = :id")
    List<Conversation> findALLByUserId(Integer id);

    List<Conversation> findAllByUsers(User user);

}
