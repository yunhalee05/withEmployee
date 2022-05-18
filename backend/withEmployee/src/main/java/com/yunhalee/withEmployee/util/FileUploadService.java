package com.yunhalee.withEmployee.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    private static String PROFILE_DIRECTORY = "profileUploads/";

    public String saveProfileImage(String id, MultipartFile multipartFile) {
        String type = StringUtils.cleanPath(multipartFile.getContentType());
        String fileName = id + "." + type.substring(type.lastIndexOf("/") + 1);
        deleteOriginalProfileImage(PROFILE_DIRECTORY + fileName);
        saveFile(Paths.get(PROFILE_DIRECTORY), fileName, multipartFile);
        return PROFILE_DIRECTORY + fileName;
    }

    private void deleteOriginalProfileImage(String filePath) {
        deleteFile(filePath);
    }

    private void deleteFile(String filePath) {
        if (new File(filePath).exists()) {
            new File(filePath).delete();
        }
    }

    private void saveFile(Path uploadPath, String fileName, MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException("Could not save file: " + fileName + ex.getMessage());
        }
    }

//    public void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) {
//        Path uploadPath = Paths.get(uploadDir);
//
//        if(!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        try(InputStream inputStream = multipartFile.getInputStream()) {
//            Path filePath = uploadPath.resolve(fileName);
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//        }catch (IOException ex){
//            throw new RuntimeException("Could not save file: " +fileName + ex.getMessage());
//        }
//    }

//    private void checkProfileDirectory(Path uploadPath) throws IOException {
//        checkDirectory(uploadPath);
//
//
//    }
//
//    private void emptyDirectory(Path uploadPath) throws IOException {
//        try {
//            Files.list(uploadPath).forEach(file -> deleteProfileFile(file));
//        } catch (IOException e) {
//            throw new RuntimeException("Could not list directory : " + uploadPath);
//        }
//    }
//
//    private void deleteDirectoryFiles(Path filePath) throws IOException {
//        if (Files.isDirectory(filePath)) {
//            emptyDirectory(filePath);
//
//        }
//    }
//
//
//
//
//
//    public void cleanDir(Path uploadPath) {
//        try {
//            Files.list(uploadPath).forEach(file->{
//                if(!Files.isDirectory(file)) {
//                    try {
//                        Files.delete(file);
//                    }catch(IOException ex) {
//                        System.out.println("Could not delete file : " + file);
//                    }
//                }
//            });
//        }catch(IOException ex2) {
//            System.out.println("Could not list directory : " + dirPath);
//        }
//    }
//
//
//    private void checkDirectory(Path uploadPath) throws IOException {
//        if(!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//    }
//
////    private void deleteFile(Path filePath) {
////        if(!Files.isDirectory(filePath)) {
////            try {
////                Files.delete(filePath);
////            }catch(IOException ex) {
////                System.out.println("Could not delete file : " + filePath);
////            }
////        }
////    }
//    private void deleteProfileFile(Path filePath) {
//        if (!Files.isDirectory(filePath)) {
//            deleteFile(filePath);
//        }
//    }

//    private void deleteFile(Path filePath) throws IOException {
//        if (Files.isDirectory(filePath)) {
//            throw new IOException("This is directory not file.");
//        }
//        try {
//            Files.deleteIfExists(filePath);
//        } catch (IOException e) {
//            throw new RuntimeException("Could not delete file : " + filePath);
//        }
//    }


}
