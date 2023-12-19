package com.demo.filemanage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Comments")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String text;

}
