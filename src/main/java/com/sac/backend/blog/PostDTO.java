package com.sac.backend.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO implements Serializable {
    private String name;
    private String phone;
    private String message;
    private MultipartFile image;
    private MultipartFile audio;
    private Boolean question_1;
    private Boolean question_2;
}
