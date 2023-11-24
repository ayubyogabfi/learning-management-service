package com.example.demo.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchArticleListBySectionTitleRequest {

    @NotEmpty(message = "Section title must not be empty")
    private String sectionTitle;
}