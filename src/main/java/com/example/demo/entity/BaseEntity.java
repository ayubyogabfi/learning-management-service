package com.example.demo.entity;

import java.time.LocalDateTime;
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
  protected LocalDateTime createdDate;

  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "created_from")
  private String createdFrom;

  @Column(name = "updated_date")
  private LocalDateTime updatedDate;

  @Column(name = "updated_by")
  private String updatedBy;

  @Column(name = "updated_from")
  private String updatedFrom;

  @Column(name = "deleted_date")
  private LocalDateTime deletedDate;
}
