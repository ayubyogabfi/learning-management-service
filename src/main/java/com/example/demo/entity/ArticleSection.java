package com.example.demo.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "td_article_section")
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSection extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  public Long id;

  @Column(name = "section_id")
  public Long sectionId;

  @Column(name = "article_id")
  public Long articleId;

  @Column(name = "body")
  public String body;
}
