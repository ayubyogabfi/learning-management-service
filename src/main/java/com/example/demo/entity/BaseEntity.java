package com.example.demo.entity;

import java.time.Instant;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

  @CreationTimestamp
  @Column(name = "created_date", updatable = false, nullable = false)
  protected Instant createdDate;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "created_from")
  private String createdFrom;

  @Column(name = "updated_date")
  private String updatedDate;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "updated_from")
  private String updatedFrom;

  @Column(name = "deleted_date")
  private String deletedDate;
}
