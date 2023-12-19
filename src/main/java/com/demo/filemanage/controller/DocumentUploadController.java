package com.demo.filemanage.controller;

import com.demo.filemanage.model.PostRequest;
import com.demo.filemanage.model.ResponseMessage;
import com.demo.filemanage.model.UploadFileResponse;
import com.demo.filemanage.service.DocumentStorageServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DocumentUploadController {

    @Autowired
    DocumentStorageServiceImpl fileStorageService;

    @PostMapping("/saveFile")
    public ResponseEntity<ResponseMessage> saveFile(@RequestParam String postRequest, @RequestPart("file") MultipartFile file) throws IOException {
        PostRequest postReq = null;
        ObjectMapper obj = new ObjectMapper();
        postReq= obj.readValue(postRequest, PostRequest.class);
        ResponseMessage responseMessage = fileStorageService.saveFile(postReq,file);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/deleteFile/{id}")
    public ResponseEntity<ResponseMessage> deleteFile(@PathVariable("id") long id) {
        ResponseMessage responseMessage = fileStorageService.deleteFile(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("/getAllFiles")
    public ResponseEntity<List<UploadFileResponse>> getAllFiles() throws Exception{
        List<UploadFileResponse> files =  fileStorageService.getAllFiles().stream().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/")
                    .path(String.valueOf(dbFile.getId()))
                    .toUriString();

            return new UploadFileResponse(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length, dbFile.getPost().getDescription()   +"Comments"+   dbFile.getPost().getCommentList());
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
}