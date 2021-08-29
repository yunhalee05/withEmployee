package com.yunhalee.withEmployee.Repository;

import com.yunhalee.withEmployee.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
}
