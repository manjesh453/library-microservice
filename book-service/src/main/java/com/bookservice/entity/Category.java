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
public class Category {
    @Id
    private String id;
    @CreatedDate
    private Date createdDate = new Date();
    @LastModifiedDate
    private Date lastModifiedDate;
    @Version
    private Long version = 0L;
    private String categoryName;
    private String categoryDescription;
    private Status status;
}
