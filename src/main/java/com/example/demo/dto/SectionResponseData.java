package com.example.demo.dto;

import lombok.Getter;

@Getter
public class SectionResponseData {

  private final String sectionTitle;
  private final String body;

  public SectionResponseData(String sectionTitle, String body) {
    this.sectionTitle = sectionTitle;
    this.body = body;
  }
}
