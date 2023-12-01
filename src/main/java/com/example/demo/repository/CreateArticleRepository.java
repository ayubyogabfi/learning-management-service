package com.example.demo.repository;

import com.example.demo.dto.CreateArticleResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreateArticleRepository extends JpaRepository<CreateArticleResponse, Long> {}
