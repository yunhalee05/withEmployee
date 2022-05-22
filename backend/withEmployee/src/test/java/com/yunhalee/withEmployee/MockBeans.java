package com.yunhalee.withEmployee;

import com.yunhalee.withEmployee.company.domain.CompanyRepository;
import com.yunhalee.withEmployee.company.service.CompanyService;
import com.yunhalee.withEmployee.conversation.domain.ConversationRepository;
import com.yunhalee.withEmployee.conversation.service.ConversationService;
import com.yunhalee.withEmployee.message.domain.MessageRepository;
import com.yunhalee.withEmployee.security.jwt.JwtUserDetailsService;
import com.yunhalee.withEmployee.team.domain.TeamRepository;
import com.yunhalee.withEmployee.user.domain.UserRepository;
import com.yunhalee.withEmployee.user.service.UserService;
import com.yunhalee.withEmployee.util.FileUploadService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MockBeans {

    @MockBean
    protected UserRepository userRepository;

    @MockBean
    protected TeamRepository teamRepository;

    @MockBean
    protected PasswordEncoder passwordEncoder;

    @MockBean
    protected FileUploadService fileUploadService;

    @MockBean
    protected UserService userService;

    @MockBean
    protected CompanyRepository companyRepository;

    @MockBean
    protected JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    protected CompanyService companyService;

    @MockBean
    protected ConversationRepository conversationRepository;

    @MockBean
    protected MessageRepository messageRepository;

    @MockBean
    protected ConversationService conversationService;


}
