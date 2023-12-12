package com.example.demo.repository;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.entity.Article;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
  @Query(
    "SELECT tas FROM ArticleSection tas\n" +
    "INNER JOIN Article ta ON ta.id = tas.articleId\n" +
    "INNER JOIN Section ts ON tas.sectionId = ts.id\n" +
    "WHERE (ta.title LIKE CONCAT('%', :keyword, '%') \n" +
    "OR ts.title LIKE CONCAT('%', :keyword, '%') \n" +
    "OR ta.body LIKE CONCAT('%', :keyword, '%' " +
    "AND createdBy = :extractedUsername ))"
  )
  List<ArticleResponse> findArticleByKeyword(@Param("keyword") String keyword, String extractedUsername);

  @Query(
    value = "SELECT tas FROM ArticleSection tas\n" +
    "INNER JOIN Article ta ON ta.id = tas.articleId\n" +
    "INNER JOIN Section ts ON tas.sectionId = ts.id\n" +
    "WHERE ta.createdBy = :extractedUsername " +
    "AND tas.deletedDate IS NULL "
  )
  List<ArticleResponse> findAllArticlesByUserLogin(String extractedUsername);

  @Query(
    value = "SELECT ta FROM Article ta INNER JOIN ArticleSection tas " +
    "ON ta.id = tas.articleId WHERE ta.title = :articleTitle " +
    "AND tas.deletedDate IS NULL AND ta.createdBy = :extractedUsername "
  )
  Optional<Article> findArticleOnArticleSection(@Param("articleTitle") String articleTitle, String extractedUsername);

  @Query(
    value = "SELECT ta FROM Article ta WHERE ta.title = :articleTitle " +
    "AND ta.deletedDate IS NULL " +
    "AND ta.createdBy = :extractedUsername "
  )
  Article findArticle(@Param("articleTitle") String articleTitle, String extractedUsername);

  @Transactional
  @Modifying
  @Query(
    value = "UPDATE Article ta SET ta.title = :articleTitle, ta.body = :body, " +
    "ta.updatedDate = CURRENT_TIMESTAMP, ta.updatedBy = :extractedUsername, " +
    "ta.updatedFrom = :extractedUsername " +
    "WHERE ta.id = :articleId AND ta.deletedDate IS NULL " +
    "AND ta.createdBy = :extractedUsername"
  )
  void updateArticle(
    @Param("articleId") Long articleId,
    @Param("articleTitle") String articleTitle,
    @Param("body") String body,
    @Param("extractedUsername") String extractedUsername
  );

  @Query(
    value = "SELECT ta FROM Article ta INNER JOIN ArticleSection tas " +
    "ON ta.id = tas.articleId WHERE tas.id = :articleSectionId " +
    "AND tas.deletedDate IS NULL " +
    "AND ta.createdBy = :extractedUsername "
  )
  List<Article> findArticleByArticleSectionId(Long articleSectionId, String extractedUsername);

  @Query(
    value = "SELECT ta from Article ta WHERE ta.id = :articleId " +
    "AND ta.createdBy = :extractedUsername AND ta.deletedDate IS NULL"
  )
  Article findArticleByArticleId(Long articleId, String extractedUsername);

  @Transactional
  @Modifying
  @Query(
    value = "UPDATE Article ta SET ta.deletedDate = CURRENT_TIMESTAMP, ta.updatedBy = :extractedUsername " +
    "WHERE ta.id = :articleId AND ta.deletedDate IS NULL " +
    "AND ta.createdBy = :extractedUsername"
  )
  void deleteArticle(@Param("articleId") Long articleId, @Param("extractedUsername") String extractedUsername);
}
