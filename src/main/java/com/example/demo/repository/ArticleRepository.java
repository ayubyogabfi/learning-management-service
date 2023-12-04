package com.example.demo.repository;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.UpdateArticleResponse;
import com.example.demo.entity.Article;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
  @Query(
    "SELECT tas FROM ArticleSection tas\n" +
    "INNER JOIN Article ta ON ta.id = tas.articleId\n" +
    "INNER JOIN Section ts ON tas.sectionId = ts.id\n" +
    "WHERE (ta.title LIKE CONCAT('%', :keyword, '%') \n" +
    "OR ts.title LIKE CONCAT('%', :keyword, '%') \n" +
    "OR ta.body LIKE CONCAT('%', :keyword, '%'))"
  )
  List<ArticleResponse> findArticleByKeyword(@Param("keyword") String keyword);

  @Query(
    value = "SELECT tas FROM ArticleSection tas\n" +
    "INNER JOIN Article ta ON ta.id = tas.articleId\n" +
    "INNER JOIN Section ts ON tas.sectionId = ts.id\n" +
    "WHERE ta.createdBy = :userId"
  )
  List<ArticleResponse> findAllArticles();

  @Query(
    value = "SELECT ta FROM Article INNER JOIN ArticleSection tas " +
    "ON ta.id = tas.article_id WHERE ta.title = :articleTitle " +
    "AND tas.deleted_date IS NULL " +
    "AND ta.createdBy = :userId "
  )
  Optional<Article> findArticleOnDatabase(@Param("articleTitle") String articleTitle, String userId);

  @Query(
          value = "SELECT"
  )
  Optional<UpdateArticleResponse> updateArticle(
          @Param("articleTitle") String articleTitle,
          @Param("sectionTitle") String sectionTitle,
          @Param("body") String body
  );


}
