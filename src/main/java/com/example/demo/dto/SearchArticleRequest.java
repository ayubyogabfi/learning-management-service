package com.example.demo.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchArticleRequest {

    @NotEmpty(message = "Article title must not be empty")
    private String articleTitle;
}