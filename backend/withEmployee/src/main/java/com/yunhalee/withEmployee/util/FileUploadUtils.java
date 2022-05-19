package com.yunhalee.withEmployee.util;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtils {

    private static String PROFILE_DIRECTORY = "profileUploads/";

    public static void saveProfileImage(String id, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(PROFILE_DIRECTORY);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(id);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ex){
            throw new IOException("Could not save file", ex);
        }

    }
    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ex){
            throw new IOException("Could not save file: " +fileName, ex);
        }
    }

    public static void cleanDir(String dir) {
        Path dirPath = Paths.get(dir);

        try {
            Files.list(dirPath).forEach(file->{
                if(!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                    }catch(IOException ex) {
                        System.out.println("Could not delete file : " + file);
                    }
                }
            });
        }catch(IOException ex2) {
            System.out.println("Could not list directory : " + dirPath);
        }
    }

    private void saveFile(Path uploadPath, String fileName, MultipartFile multipartFile) throws IOException {
        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ex){
            throw new IOException("Could not save file: " +fileName, ex);
        }
    }

    private void checkDirectory(Path uploadPath) throws IOException {
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    }

    private void deleteFile(Path uploadPath, String fileName) {
        if (new File(uploadPath + fileName).exists()){
            new File(uploadPath + fileName).delete();
        }
    }


}
