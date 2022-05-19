package com.sac.backend.blog;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.sac.backend.files.FileStorage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 51)
    private String name;
    @Column(length = 15)
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(updatable = false)
    private Date created_at;

    private Boolean isActive = false;

    @Column(length = 2000)
    private String message;

    @OneToOne(cascade = {CascadeType.ALL})
    private FileStorage image;
    @OneToOne(cascade = {CascadeType.ALL})
    private FileStorage audio;

    @Column(columnDefinition = "json")
    @JsonRawValue
    private String questions;

}
