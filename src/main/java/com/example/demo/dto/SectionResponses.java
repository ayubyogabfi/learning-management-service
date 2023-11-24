package com.example.demo.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class SectionResponses<T> {

  private final List<T> data;

  public SectionResponses(List<T> data) {
    this.data = data;
  }
}
