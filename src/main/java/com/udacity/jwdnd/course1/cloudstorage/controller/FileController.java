package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping
    public String loadFile(@RequestParam("fileUpload") MultipartFile mFile, @ModelAttribute File file, Model model, Authentication authentication) throws IOException {
        file.setUserId(userService.getUser(authentication.getName()).getUserId());
        file.setFileName(mFile.getOriginalFilename());
        file.setContentType(mFile.getContentType());
        file.setFileData(mFile.getBytes());
        file.setFileSize(String.valueOf(mFile.getSize()));
        if (mFile.getOriginalFilename()==null || mFile.getOriginalFilename().equals("")){
            model.addAttribute("emptyFile", "emptyFile");
        } else if (fileService.fileNameAlreadyExists(file)){
            model.addAttribute("fileDuplicate", "fileDuplicate");
        } else {
            model.addAttribute("success", "success");

            fileService.saveFile(file);
        }
        return "result";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Model model){
        fileService.deleteFile(fileId);
        model.addAttribute("delete", "delete");
        return "result";
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer fileId){
        File file = fileService.getOneFile(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String sizeFileTooLargeExp(){
        return "fileError";
    }
}
