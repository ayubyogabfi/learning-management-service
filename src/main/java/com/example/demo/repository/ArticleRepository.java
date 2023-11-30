package com.example.demo.repository;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.entity.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
  @Query(
    "SELECT tas FROM ArticleSection tas\n" +
    "INNER JOIN Article ta ON ta.id = tas.articleId\n" +
    "INNER JOIN Section ts ON tas.sectionId = ts.id\n" +
    "WHERE (ta.articleTitle LIKE CONCAT('%', :keyword, '%') \n" +
    "OR ts.title LIKE CONCAT('%', :keyword, '%') \n" +
    "OR ta.body LIKE CONCAT('%', :keyword, '%'))"
  )
  List<ArticleResponse> findArticleByKeyword(@Param("keyword") String keyword);

  @Query(
    value = "SELECT tas FROM ArticleSection tas\n" +
    "INNER JOIN Article ta ON ta.id = tas.articleId\n" +
    "INNER JOIN Section ts ON tas.sectionId = ts.id\n" +
    "WHERE ta.createdBy = :username"
  )
  List<ArticleResponse> findAllArticles();
}
