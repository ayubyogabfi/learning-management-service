package com.example.demo.repository;

import com.example.demo.entity.ArticleSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleSectionRepository
  extends JpaRepository<ArticleSection, Long>, JpaSpecificationExecutor<ArticleSection> {
  @Transactional
  @Modifying
  @Query(
    value = "UPDATE ArticleSection tas SET tas.sectionId = :sectionId, \n" +
    "tas.updatedDate = CURRENT_TIMESTAMP, tas.updatedBy = :extractedUsername, \n" +
    "tas.updatedFrom = :extractedUsername \n" +
    "WHERE tas.id = :articleSectionId AND tas.deletedDate IS NULL \n" +
    "AND tas.createdBy = :extractedUsername"
  )
  void updateArticleSectionById(Long articleSectionId, Long sectionId, String extractedUsername);
}
