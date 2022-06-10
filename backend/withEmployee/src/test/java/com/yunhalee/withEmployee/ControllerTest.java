package com.yunhalee.withEmployee;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartBody;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc // -> webAppContextSetup(webApplicationContext)
@AutoConfigureRestDocs // -> apply(documentationConfiguration(restDocumentation))
@SpringBootTest
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("multipartFile", "image.png", "image/png",
            "<<png data>>".getBytes());
        MockMultipartFile userRequest = new MockMultipartFile("userRequest", "",
            "application/json", "{ \"version\": \"1.0\"}".getBytes());

        this.mockMvc.perform(fileUpload("/users").file(multipartFile).file(userRequest)
            .accept(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isOk())
            .andDo(document("multipartFile", requestPartBody("userRequest")));
    }


}
