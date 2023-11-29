package com.example.demo.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "td_article_section")
public class ArticleSection extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  public Long id;

  @Column(name = "section_id")
  public String sectionId;

  @Column(name = "article_id")
  public String articleId;

  @Column(name = "body")
  public String body;
}
