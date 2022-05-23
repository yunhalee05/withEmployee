package com.yunhalee.withEmployee.util;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringRunner.class)
@SpringBootTest
class FileUploadServiceTest {

    @Autowired
    FileUploadService fileUploadService;


    @Test
    public void testUploadProfile() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("test_image", "test_image", "image/png", new byte[10]);
        fileUploadService.uploadProfileImage("1", multipartFile);

    }

}