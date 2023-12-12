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

  @Query(
    value = "SELECT tas FROM ArticleSection tas WHERE tas.id = :articleSectionId " +
    "AND createdBy = :extractedUsername"
  )
  ArticleSection findOneByArticleSectionId(Long articleSectionId, String extractedUsername);

  @Transactional
  @Modifying
  @Query(
    value = "UPDATE ArticleSection tas SET tas.deletedDate = CURRENT_TIMESTAMP, \n" +
    "tas.updatedBy = :extractedUsername " +
    "WHERE tas.articleId = :articleId AND tas.createdBy = :extractedUsername"
  )
  void deleteArticleSectionByArticleId(Long articleId, String extractedUsername);

  @Transactional
  @Modifying
  @Query(
    value = "UPDATE ArticleSection tas SET tas.deletedDate = CURRENT_TIMESTAMP, \n" +
    "tas.updatedBy = :extractedUsername " +
    "WHERE tas.sectionId = :sectionId AND tas.createdBy = :extractedUsername"
  )
  void deleteArticleSectionBySectionId(Long sectionId, String extractedUsername);
}
