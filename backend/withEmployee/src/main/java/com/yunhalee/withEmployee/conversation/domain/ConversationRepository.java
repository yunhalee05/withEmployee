package com.yunhalee.withEmployee.conversation.domain;

import com.yunhalee.withEmployee.user.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Integer> {


    @Query(value = "SELECT c FROM Conversation c LEFT JOIN FETCH c.users u LEFT JOIN FETCH u.companies p WHERE u.id = :id")
    List<Conversation> fintByUserId(Integer id);

    List<Conversation> findByUsers(Optional<User> user);

}
