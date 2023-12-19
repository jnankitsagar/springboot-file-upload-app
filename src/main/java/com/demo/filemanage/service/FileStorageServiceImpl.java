package com.demo.filemanage.service;

import com.demo.filemanage.entity.File;
import com.demo.filemanage.entity.Post;
import com.demo.filemanage.exception.FileStorageException;
import com.demo.filemanage.exception.MyFileNotFoundException;
import com.demo.filemanage.model.PostRequest;
import com.demo.filemanage.model.PostResponse;
import com.demo.filemanage.model.ResponseMessage;
import com.demo.filemanage.repository.FileRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileStorageServiceImpl implements FileStorageServices {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    HttpServletResponse response;

    @Autowired
    WebClient webClient;

    public ResponseMessage saveFile(PostRequest postRequest, MultipartFile file) {
        try {
            PostResponse postResponse = getPostResponse(postRequest);
            fileRepository.save(File.builder().name(file.getOriginalFilename()).type(file.getContentType()).data(file.getBytes()).post(Post.builder().title(Objects.requireNonNull(postResponse).getTitle()).description(postResponse.getBody()).commentList(postResponse.getCommentList()).build()).build());
            return new ResponseMessage("File saved successfully");
        } catch (Exception e) {
            throw new FileStorageException("File not saved");
        }
    }

    private PostResponse getPostResponse(PostRequest postRequest) {
        return webClient.post().uri("/posts").header(HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_JSON_VALUE).body(Mono.just(postRequest),
                PostRequest.class).retrieve().bodyToMono(PostResponse.class).block();
    }


    @Override
    public ResponseMessage deleteFile(long id) {
        try {
            fileRepository.deleteById(id);
            return new ResponseMessage("File deleted successfully");
        } catch (Exception e) {
            throw new MyFileNotFoundException("File not deleted");
        }
    }

    @Override
    public File downloadFile(long id) {
        return fileRepository.findById(id).orElseThrow(() -> new MyFileNotFoundException("File not found with id " + id));
    }

    @Override
    public List<File> getAllFiles() {
        List<File> list = new ArrayList<>();
        fileRepository.findAll().forEach(list::add);
        return list;
    }

}





