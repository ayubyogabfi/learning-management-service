package com.example.demo.repository;

import com.example.demo.entity.Section;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

  @Query(
     value = "SELECT ts from Section ts WHERE createdBy = :extractedUsername " +
             "ORDER BY ts.title ASC"
  )
  List<Section> findAllByOrderByTitleAscAndUserLogin(String extractedUsername);

  @Query(
    value = "SELECT ts from Section ts INNER JOIN ArticleSection tas \n" +
    "ON ts.id = tas.sectionId WHERE ts.id = :sectionId \n" +
    "AND tas.deletedDate IS NULL \n " +
    "AND ts.createdBy = :extractedUsername"
  )
  List<Section> findSectionIdOnArticleSection(String sectionId, String extractedUsername);

  @Query(
    value = "SELECT ts from Section ts INNER JOIN ArticleSection tas \n" +
    "ON ts.id = tas.sectionId WHERE ts.title = :sectionTitle \n" +
    "AND tas.deletedDate IS NULL \n " +
    "AND ts.createdBy = :extractedUsername"
  )
  List<Section> findSectionTitleOnArticleSection(String sectionTitle, String extractedUsername);

  @Modifying
  @Query(
    value = "INSERT INTO Section (title, createdBy, createdFrom, createdDate, deletedDate) " +
            "values (:sectionTitle, :extractedUsername, 'localhost', now(), NULL)"
  )
  void createSectionBySectionTitle(String sectionTitle, String extractedUsername);
}
