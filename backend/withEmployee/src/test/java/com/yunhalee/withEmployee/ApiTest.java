package com.yunhalee.withEmployee;

import com.yunhalee.withEmployee.company.service.CompanyService;
import com.yunhalee.withEmployee.conversation.service.ConversationService;
import com.yunhalee.withEmployee.security.jwt.JwtUserDetailsService;
import com.yunhalee.withEmployee.team.service.TeamService;
import com.yunhalee.withEmployee.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false) // -> webAppContextSetup(webApplicationContext)
@AutoConfigureRestDocs // -> apply(documentationConfiguration(restDocumentation))
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@TestPropertySource(locations = "/config/application-test.properties")
public abstract class ApiTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected PasswordEncoder passwordEncoder;

    @MockBean
    protected UserService userService;

    @MockBean
    protected JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    protected CompanyService companyService;

    @MockBean
    protected ConversationService conversationService;

    @MockBean
    protected TeamService teamService;

}
