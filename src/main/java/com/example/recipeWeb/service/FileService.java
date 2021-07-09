package com.example.recipeWeb.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.Arrays;
import java.util.List;

@Component
public class FileService {
    private String path = "c:/ServerRepo/RecipeStore/image/recipe/";

    public void saveFile(String username, Long recipeId, MultipartFile file){
        String basePath = path + username;

        File div = new File(basePath);
        if (!div.exists()) div.mkdirs();

        try {
            String contentType = file.getContentType();
            String originalFileExtension =
                    switch (contentType) {
                        case "image/jpeg", "image/jpg" -> ".jpg";
                        case "image/png" -> ".png";
                        case "image/gif" -> ".gif";
                        default -> throw new IllegalStateException("잘못된 형식 입니다.");
                    };

            String destPath = basePath + "/" + recipeId + originalFileExtension;
            File destFile = new File(destPath);

            file.transferTo(destFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findFile(String username, Long recipeId) {
        String basePath = path + username;
        String fullName = "";

        File div = new File(basePath);
        if(div.exists() && div.listFiles().length != 0 ) {
            File[] files = div.listFiles();

            for(File file : files) {
                String filename = file.getName().split("\\.")[0];

                if(filename.equals(recipeId.toString())) {
                    fullName = file.getName();
                    break;
                } else {
                    fullName = "";
                }
            }
        }

        System.out.println("fullName : " + fullName);

        return fullName;
    }

    public boolean deleteFile(String username, Long recipeId) {
        boolean isDelete = false;

        String filename = findFile(username, recipeId);
        String filepath = path + username + "/" + filename;

        File file = new File(filepath);
        if(file.exists()) {
            isDelete = file.delete();
        }

        return isDelete;
    }

    public void updateFile(String username, Long recipeId, MultipartFile mFile) {
        //deleteFile(username, recipeId);
        saveFile(username, recipeId, mFile);
    }
}
