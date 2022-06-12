package com.sac.backend.blog;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.sac.backend.files.FileStorage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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

    private Boolean question_1;
    private Boolean question_2;
}
