package com.example.demo.repository;

import com.example.demo.dto.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByArticleTitle(String articleTitle);

    @Query("SELECT a FROM Article a WHERE a.section.title = :sectionTitle")
    List<Article> findBySectionTitle(@Param("sectionTitle") String sectionTitle);
}
