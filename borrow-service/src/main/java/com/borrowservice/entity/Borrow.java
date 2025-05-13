package com.borrowservice.entity;

import com.borrowservice.shared.Status;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
public class Borrow {
    @Id
    private String id;
    @CreatedDate
    private Date createdDate = new Date();
    @LastModifiedDate
    private Date lastModifiedDate;
    @Version
    private Long version = 0L;
    private String bookId;
    private String userId;
    private Date borrowDate;
    private Status status;
    private String approvedBy;
    private Date returnDate;
}
