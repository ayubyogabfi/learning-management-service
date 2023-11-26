package com.example.demo.repository;

import com.example.demo.dto.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {
  List<Article> findByArticleTitle(String articleTitle);

  @Query("Select tas from td_article_section tas\n" +
          "INNER JOIN tx_article ta ON ta.id = tas.article_id\n" +
          "INNER JOIN tm_section ts ON tas.section_id = ts.id WHERE tas.created_by = userID ASC")
  List<Article> findBySectionTitle(@Param("sectionTitle") String sectionTitle);
}
