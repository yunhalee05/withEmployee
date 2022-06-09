package com.yunhalee.withEmployee;

import com.yunhalee.withEmployee.company.domain.CompanyRepository;
import com.yunhalee.withEmployee.conversation.domain.ConversationRepository;
import com.yunhalee.withEmployee.message.domain.MessageRepository;
import com.yunhalee.withEmployee.team.domain.TeamRepository;
import com.yunhalee.withEmployee.user.domain.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@TestPropertySource(locations = "/config/application-test.properties")
@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class RepositoryTest {

    @Autowired
    protected ConversationRepository conversationRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CompanyRepository companyRepository;

    @Autowired
    protected MessageRepository messageRepository;

    @Autowired
    protected TeamRepository teamRepository;

}
