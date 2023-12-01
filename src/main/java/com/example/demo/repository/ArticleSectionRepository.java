package com.example.demo.repository;

import com.example.demo.entity.Article;
import com.example.demo.entity.ArticleSection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArticleSectionRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
  Optional<ArticleSection> findArticleSectionByArticleTitleAndSectionTitleOrSectionId(
    String articleTitle,
    String sectionTitle,
    String sectionId
  );
}
