package com.essobhi.bookscape.service.Impl;

import com.essobhi.bookscape.domain.Book;
import com.essobhi.bookscape.service.IFileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;
import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
public class FileStorageServiceImpl implements IFileStorageService {
    @Value("${application.file.upload.photos-output-path}")
    private String fileUploadPath;

    @Override
    public String saveFile(
            @NonNull MultipartFile sourceFile,
            @NonNull Integer userId) {
        final String fileUploadSubPath = "users" + separator +userId;
        return uploadFile(sourceFile, fileUploadSubPath);
    }

     private String uploadFile(
             @NonNull MultipartFile sourceFile,
             @NonNull String fileUploadSubPath){
        final String finalUploadFile = fileUploadPath + separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadFile);
        if(!targetFolder.exists()){
            boolean folderCreated = targetFolder.mkdirs();
            if(!folderCreated){
                log.warn("Failed to create the target folder");
                return null;
            }
        }
        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        String targetFilePath = fileUploadPath + separator + currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File saved to "+ targetFilePath);
            return targetFilePath;
        }catch (IOException e){
            log.error("File was not saved ",e);
        }
        return null;
    }

    private  String getFileExtension (String fileName){
        if(fileName == null || fileName.isEmpty()){
            return  "";
        }
        int lastDotIndex = fileName.lastIndexOf(".");
        if(lastDotIndex == -1){
            return  "";
        }
        return fileName.substring(lastDotIndex +1).toLowerCase();
    }
}
