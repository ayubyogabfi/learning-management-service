package com.example.demo.repository;

import com.example.demo.entity.ArticleSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArticleSectionRepository
  extends JpaRepository<ArticleSection, Long>, JpaSpecificationExecutor<ArticleSection> {}
