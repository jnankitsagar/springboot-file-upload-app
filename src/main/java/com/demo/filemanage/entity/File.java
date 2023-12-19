package com.demo.filemanage.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "File")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private long id;
    private String name;
    private String type;

    @Lob
    private byte[] data;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private Post post;

}
