package com.demo.filemanage.controller;

import com.demo.filemanage.entity.File;
import com.demo.filemanage.model.PostRequest;
import com.demo.filemanage.model.ResponseMessage;
import com.demo.filemanage.model.UploadFileResponse;
import com.demo.filemanage.service.FileStorageServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileUploadController {

    @Autowired
    FileStorageServiceImpl fileStorageService;

    @PostMapping("/saveFile")
    public ResponseEntity<ResponseMessage> saveFile(@RequestPart String postRequest, @RequestPart("file") MultipartFile file) throws JsonProcessingException {
        PostRequest postReq = new PostRequest();
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

    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") long id) {
        File file = fileStorageService.downloadFile(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(new ByteArrayResource(file.getData()));
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