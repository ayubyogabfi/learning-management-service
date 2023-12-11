package com.example.demo.entity;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Data
@MappedSuperclass
public class BaseEntity {

  @CreationTimestamp
  @Column(name = "created_date", updatable = false, nullable = false)
  protected ZonedDateTime createdDate;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "created_from")
  private String createdFrom;

  @Column(name = "updated_date")
  private ZonedDateTime updatedDate;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "updated_from")
  private String updatedFrom;

  @Column(name = "deleted_date")
  private ZonedDateTime deletedDate;
}
