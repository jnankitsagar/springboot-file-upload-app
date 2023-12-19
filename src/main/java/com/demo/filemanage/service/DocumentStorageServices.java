package com.demo.filemanage.service;

import com.demo.filemanage.entity.Document;
import com.demo.filemanage.model.PostRequest;
import com.demo.filemanage.model.ResponseMessage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface DocumentStorageServices {

    ResponseMessage saveFile(PostRequest postRequest, MultipartFile file) throws IOException;

    ResponseMessage deleteFile(long id);

    List<Document> getAllFiles();

}
