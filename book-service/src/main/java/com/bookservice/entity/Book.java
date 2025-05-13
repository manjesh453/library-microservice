package com.bookservice.entity;

import com.bookservice.shared.Status;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
public class Book {
    @Id
    private String id;
    @CreatedDate
    private Date createdDate = new Date();
    @LastModifiedDate
    private Date lastModifiedDate;
    @Version
    private Long version = 0L;
    private String title;
    private String description;
    private String authorName;
    private String categoryId;
    private int numberOfBooks;
    private Status status;

}
