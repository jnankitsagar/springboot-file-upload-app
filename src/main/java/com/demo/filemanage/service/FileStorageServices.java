package com.demo.filemanage.service;

import com.demo.filemanage.entity.File;
import com.demo.filemanage.model.PostRequest;
import com.demo.filemanage.model.ResponseMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface FileStorageServices {

    ResponseMessage saveFile(PostRequest postRequest, MultipartFile file);

    ResponseMessage deleteFile(long id);

    File downloadFile(long id);

   List<File> getAllFiles();

}
