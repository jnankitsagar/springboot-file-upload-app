package com.demo.filemanage.controller;

import com.demo.filemanage.entity.Document;
import com.demo.filemanage.entity.Post;
import com.demo.filemanage.model.ResponseMessage;
import com.demo.filemanage.service.DocumentStorageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DocumentUploadControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentStorageServiceImpl fileStorageService;

    @Test
    public void whenFileUploaded_thenVerifyStatus() throws Exception {
        Mockito.when(fileStorageService.saveFile(any(), any(MockMultipartFile.class))).thenReturn(new ResponseMessage("File Upload successfully"));
        MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        mockMvc.perform(multipart("/saveFile").file(file).param("postRequest", "{\n" + "    \"title\": \"foo\",\n" + "    \"body\": \"bar\",\n" + "    \"userId\": 2,\n" + "    \"commentList\": [\n" + "        {\n" + "            \"text\": \"test\"\n" + "        },\n" + "        {\n" + "            \"text\": \"test1\"\n" + "        }\n" + "    ]\n" + "} ")).andExpect(status().isOk());
    }

    @Test
    public void whenDeleteFile_thenVerifyStatus() throws Exception {
        Mockito.when(fileStorageService.deleteFile(anyLong())).thenReturn(new ResponseMessage("File deleted successfully"));
        MvcResult requestResult = mockMvc.perform(delete("/deleteFile/1")).andExpect(status().isOk()).andReturn();
        assertNotNull(requestResult, "response should not be null");
    }

    @Test
    public void whenGetAllFiles_thenVerifyStatus() throws Exception {
        BufferedImage bImage = ImageIO.read(new File("./uploads/test.jpeg"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos);
        byte[] data = bos.toByteArray();
        Resource resource = new ByteArrayResource(data);
        List<Document> list = new ArrayList<>();
        Document.builder().id(1).post(Post.builder().id(1).build()).build();
        list.add(Document.builder().id(1).data(data).post(Post.builder().id(1).build()).build());
        Mockito.when(fileStorageService.getAllFiles()).thenReturn(list);
        MvcResult requestResult = mockMvc.perform(get("/getAllFiles")).andExpect(status().isOk()).andReturn();
        assertNotNull(requestResult, "response should not be null");
    }
}
