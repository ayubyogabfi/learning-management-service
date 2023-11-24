package com.example.demo.repository;

import com.example.demo.dto.ArticleResponse;
import com.example.demo.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT tas FROM ArticleSection tas\n" +
            "INNER JOIN Article ta ON ta.id = tas.article_id\n" +
            "INNER JOIN Section ts ON tas.section_id = ts.id\n" +
            "WHERE (ta.articleTitle LIKE CONCAT('%', :articleTitle, '%') \n" +
            "OR ta.body LIKE CONCAT('%', :articleTitle, '%'))")
    List<ArticleResponse> findByArticleTitle(@Param("articleTitle") String articleTitle);

}
