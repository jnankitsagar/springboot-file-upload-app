package com.demo.filemanage.controller;

import com.demo.filemanage.model.ResponseMessage;
import com.demo.filemanage.service.FileStorageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FileUploadControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileStorageServiceImpl fileStorageService;

    //@Test
    /*public void whenFileUploaded_thenVerifyStatus()
            throws Exception {
        Mockito.when(fileStorageService.saveFile((any(MockMultipartFile.class),))).thenReturn(new ResponseMessage("File Upload successfully"));
        MockMultipartFile file = new MockMultipartFile("file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        mockMvc.perform(multipart("/saveFile").file(file))
                .andExpect(status().isOk());
        then(this.fileStorageService).should().saveFile(file);
    }*/
}
