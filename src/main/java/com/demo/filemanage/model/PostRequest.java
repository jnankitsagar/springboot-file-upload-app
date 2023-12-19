package com.demo.filemanage.model;

import com.demo.filemanage.entity.Comment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostRequest {
    private String title;
    private String body;
    private long userId;
    private List<Comment> commentList;
}
