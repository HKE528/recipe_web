//package com.example.recipeWeb.service;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.FileSystemException;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class FileService {
//    private String path = "c:/ServerRepo/RecipeStore/image/recipe/";
//
//    public void saveFile(String username, Long recipeId, MultipartFile file){
//        String basePath = path + username;
//
//        File div = new File(basePath);
//        if (!div.exists()) div.mkdirs();
//
//        try {
//            String contentType = file.getContentType();
//            String originalFileExtension =
//                    switch (contentType) {
//                        case "image/jpeg", "image/jpg" -> ".jpg";
//                        case "image/png" -> ".png";
//                        case "image/gif" -> ".gif";
//                        default -> throw new IllegalStateException("잘못된 형식 입니다.");
//                    };
//
//            String destPath = basePath + "/" + recipeId + originalFileExtension;
//            File destFile = new File(destPath);
//
//            file.transferTo(destFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String findFile(String username, Long recipeId) {
//        String basePath = path + username;
//        String filePath = "";
//
//        File div = new File(basePath);
//        if (!div.exists()) {
//            filePath = "";
//        } else {
//            File[] files = div.listFiles();
//
//            for(File file : files) {
//
//                String fullName = file.getName();
//                String filename = fullName.split("\\.")[0];
//
//                if(filename == recipeId.toString()) {
//                    filePath = basePath + "/" + filename;
//
//                    break;
//                }
//            }
//        }
//
//        if(div.exists()) {
//            File[] files = div.listFiles();
//
//            for(File file : files) {
//
//                String fullName = file.getName();
//                String filename = fullName.split("\\.")[0];
//
//                if(filename.equals(recipeId.toString())) {
//                    filePath = basePath + "/" + fullName;
//
//                    break;
//                }
//            }
//        }
//
//        System.out.println("filePath : " + filePath);
//
//        return filePath;
//    }
//
//    public boolean deleteFile(String username, Long recipeId) {
//        boolean isDelete = false;
//
//        String filepath = findFile(username, recipeId);
//
//        File file = new File(filepath);
//        if(!file.exists()) {
//            isDelete = file.delete();
//        }
//
//        return isDelete;
//    }
//
//    public void updateFile(String username, Long recipeId, MultipartFile mFile) {
//        if(deleteFile(username, recipeId)) {
//            saveFile(username, recipeId, mFile);
//        }
//    }
//}
