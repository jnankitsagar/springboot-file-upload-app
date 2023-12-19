package com.demo.filemanage.service;

import com.demo.filemanage.entity.Document;
import com.demo.filemanage.entity.Post;
import com.demo.filemanage.exception.FileStorageException;
import com.demo.filemanage.exception.MyFileNotFoundException;
import com.demo.filemanage.model.PostRequest;
import com.demo.filemanage.model.PostResponse;
import com.demo.filemanage.model.ResponseMessage;
import com.demo.filemanage.repository.DocumentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DocumentStorageServiceImpl implements DocumentStorageServices {

    @Autowired
    DocumentRepository fileRepository;

    @Autowired
    WebClient webClient;

    @CircuitBreaker(name = "saveFall",fallbackMethod = "saveFallBack")
    public ResponseMessage saveFile(PostRequest postRequest, MultipartFile file) throws IOException {
            PostResponse postResponse = webClient.post().uri("/posts").header(HttpHeaders.CONTENT_TYPE,
                    MediaType.APPLICATION_JSON_VALUE).body(Mono.just(postRequest),
                    PostRequest.class).retrieve().bodyToMono(PostResponse.class).block();
            fileRepository.save(Document.builder().name(file.getOriginalFilename()).type(file.getContentType()).data(file.getBytes()).post(Post.builder().title(Objects.requireNonNull(postResponse).getTitle()).description(postResponse.getBody()).commentList(postResponse.getCommentList()).build()).build());
            return new ResponseMessage("File saved successfully");
    }

    public ResponseMessage saveFallBack(Throwable exception) {
       return new ResponseMessage("File not saved");
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
    public List<Document> getAllFiles() {
        List<Document> list = new ArrayList<>();
        fileRepository.findAll().forEach(list::add);
        return list;
    }

}





