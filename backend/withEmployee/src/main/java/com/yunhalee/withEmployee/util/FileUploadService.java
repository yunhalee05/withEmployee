package com.yunhalee.withEmployee.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class FileUploadService {

    private static String PROFILE_DIRECTORY = "profileUploads/";
    private static String MESSAGE_DIRECTORY = "messageUploads";


    public String uploadMessageImage(MultipartFile multipartFile) {
        String fileName = System.currentTimeMillis() + StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            saveFile(MESSAGE_DIRECTORY, fileName, multipartFile);
        } catch (IOException e) {
            new RuntimeException("Could not save file : " + multipartFile.getOriginalFilename());
        }
        return "/" + MESSAGE_DIRECTORY + "/" + fileName;
    }

    public void saveFile(String uploadDir, String fileName, MultipartFile multipartFile)
        throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        checkDirectory(uploadPath);
        uploadFile(uploadPath, fileName, multipartFile);
    }

    private void checkDirectory(Path uploadPath) throws IOException {
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException ex) {
            throw new IOException("Could not create directory", ex);
        }
    }

    public String uploadProfileImage(String id, MultipartFile multipartFile) {
        String type = StringUtils.cleanPath(multipartFile.getContentType());
        String fileName = id + "." + type.substring(type.lastIndexOf("/") + 1);
        deleteOriginalProfileImage(PROFILE_DIRECTORY + fileName);
        uploadFile(Paths.get(PROFILE_DIRECTORY), fileName, multipartFile);
        return "/" + PROFILE_DIRECTORY + fileName;
    }

    private void deleteOriginalProfileImage(String filePath) {
        deleteFile(filePath);
    }

    private void deleteFile(String filePath) {
        if (new File(filePath).exists()) {
            new File(filePath).delete();
        }
    }

    private void uploadFile(Path uploadPath, String fileName, MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException("Could not save file: " + fileName + ex.getMessage());
        }
    }
}
