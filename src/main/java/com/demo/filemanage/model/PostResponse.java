package com.demo.filemanage.model;

import com.demo.filemanage.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PostResponse {
    private String title;
    private String body;
    private long userId;
    private long id;
    private List<Comment> commentList;
}
