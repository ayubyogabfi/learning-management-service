package com.example.demo.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tx_article")
public class Article extends BaseEntity {

  @Id
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private String sectionTitle;

  @Column
  private String articleTitle;

  @Column
  private String body;
}
