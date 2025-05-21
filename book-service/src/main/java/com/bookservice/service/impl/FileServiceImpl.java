package com.bookservice.service.impl;


import com.bookservice.exception.ImageAlreadyFound;
import com.bookservice.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = path + File.separator + fileName;
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
        File imageFile = new File(filePath);
        if (imageFile.exists()) {
            return fileName;
        }
        try {
            Files.copy(file.getInputStream(), Paths.get(filePath));
        }catch (Exception e){
            throw new ImageAlreadyFound("Sorry image could not be uploaded");
        }
        return fileName;
    }


    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        return new FileInputStream(fullPath);
    }

    @Override
    public void deleteImage(String path, String filename) throws IOException {
        String fullPath=path+File.separator+filename;
        Files.delete(Path.of(fullPath));
    }
}




