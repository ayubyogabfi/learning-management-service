package com.example.demo.dto;


import lombok.Getter;

import java.util.List;

@Getter
public class SectionResponses<T> {
    private final List<T> data;

    public SectionResponses(List<T> data) {
        this.data = data;
    }
}