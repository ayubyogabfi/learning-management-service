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
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  public Long id;

  @Column
  public String sectionId;

  @Column
  public String articleId;

  @Column
  public String body;
}
