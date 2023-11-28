package com.example.demo.entity;

import java.time.ZonedDateTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

  //  @CreationTimestamp
  @Column
  private ZonedDateTime createdDate;

  @Column
  private String createdBy;

  @Column
  private String createdFrom;

  @Column
  private ZonedDateTime updatedDate;

  @Column
  private String updatedBy;

  @Column
  private String updatedFrom;
}
