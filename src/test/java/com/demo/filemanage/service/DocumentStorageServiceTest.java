package com.demo.filemanage.service;

import com.demo.filemanage.entity.Document;
import com.demo.filemanage.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DocumentStorageServiceTest {
    @Mock
    DocumentRepository fileRepository;

    @Mock
    WebClient webClient;

    @InjectMocks
    DocumentStorageServiceImpl documentStorageService;

    @Test
    public void whenGetAllDocuments_thenVerifyStatus(){
        List<Document> documentList =  new ArrayList<>();
        documentList.add(Document.builder().name("fileUpload").build());
        Mockito.when(fileRepository.findAll()).thenReturn(documentList);
        List<Document> documents =  documentStorageService.getAllFiles();
        assertNotNull(documents, "response should not be null");
    }

    @Test
    public void whenDeleteDocuments_thenVerifyStatus(){
        documentStorageService.deleteFile(1);
        verify(fileRepository, times(1));
    }

}
