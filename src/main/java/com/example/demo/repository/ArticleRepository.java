package com.example.demo.repository;

import com.example.demo.dto.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByArticleTitle(String articleTitle);

}
