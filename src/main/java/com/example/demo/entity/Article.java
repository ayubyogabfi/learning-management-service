package com.example.demo.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tx_article")
public class Article extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "section_title")
  private String sectionTitle;

  @Column(name = "article_title")
  private String articleTitle;

  @Column(name = "body")
  private String body;
}
