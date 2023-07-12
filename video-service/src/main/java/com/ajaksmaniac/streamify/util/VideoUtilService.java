package com.ajaksmaniac.streamify.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class VideoUtilService {
    private Path foundFile;
    public static void saveFile(String id,String fileName, MultipartFile multipartFile) throws IOException{
        Path uploadPath = Paths.get("Files-Upload");

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(id+"-"+fileName);
            Files.copy(inputStream,filePath,StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new IOException("Couldnt save file:" + fileName);
        }
    }
    public Resource getFileAsResource(String id) throws IOException{


        Path dirPath = Paths.get("Files-Upload");
        Files.list(dirPath).forEach(f ->{
            if(f.getFileName().toString().startsWith(id+"-")){
                foundFile = f;
                return;
            }
        });

        if(foundFile != null){
            return new UrlResource(foundFile.toUri());
        }
        return null;
    }

    public static void deleteFile(String id) throws IOException {
        Path dirPath = Paths.get("Files-Upload");
        Files.list(dirPath).forEach(f ->{
            if(f.getFileName().toString().startsWith(id+"-")){

                try {
                    Files.delete(f.toAbsolutePath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public static void updateVideo(String id,String fileName, MultipartFile file) throws IOException {
        deleteFile(id);
        saveFile(id,fileName, file);
    }

    public static void updateVideo(String id,String fileName) throws IOException {
        Path dirPath = Paths.get("Files-upload");
        Files.list(dirPath).forEach(f ->{
            if(f.getFileName().toString().startsWith(id+"-")){

                try {
                    Files.move(f,f.resolveSibling(id+"-"+fileName),StandardCopyOption.REPLACE_EXISTING);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}