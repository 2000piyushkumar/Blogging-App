package com.scaler.blogapi.commons;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.UUID;
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    UUID id;

    @CreatedDate
    @Column(nullable = false)
    Date createdAt;

    @PrePersist
    private void OnCreate(){
        Date date = new Date();
        this.setCreatedAt(date);
    }
}
