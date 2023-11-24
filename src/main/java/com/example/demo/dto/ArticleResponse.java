package com.example.demo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {

    private String sectionTitle;

    private String articleTitle;

    private String body;

}