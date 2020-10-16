package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getAllFiles(Integer userId){
        return fileMapper.getFiles(userId);
    }

    public File getOneFile(Integer fileId){
        return fileMapper.getOneFile(fileId);
    }

    public void saveFile(File file){

        fileMapper.insertFile(file);
    }

    public void deleteFile(Integer fileId){
        fileMapper.deleteFile(fileId);
    }

    public Boolean fileNameAlreadyExists(File file){
        Integer userId = file.getUserId();
        Boolean fileAlreadyExists = false;
        List<String> fileNames = new ArrayList<>();
        List<File> fileList = fileMapper.getFiles(userId);
        for (File f: fileList) {
            if (f.getFileName().equals(file.getFileName())){
                fileAlreadyExists = true;
            }
        }
        return fileAlreadyExists;
    }
}
