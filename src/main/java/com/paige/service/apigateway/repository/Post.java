package com.paige.service.apigateway.repository;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

//@RedisHash("posts")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    private String id;
    private String title;
    private String content;

    @CreatedDate
    private LocalDateTime createdDate;
}