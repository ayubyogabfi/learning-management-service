package com.example.demo.repository;

import com.example.demo.entity.Section;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
  @Query(value = "SELECT ts from Section ts WHERE createdBy = :extractedUsername " + "ORDER BY ts.title ASC")
  List<Section> findAllByTitleOrderAscAndUserLogin(String extractedUsername);

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

  @Query(
    value = "SELECT ts from Section ts WHERE ts.title = :sectionTitle AND createdBy = :extractedUsername " +
    "ORDER BY ts.title ASC"
  )
  List<Section> findSectionBySectionTitleAndUserLogin(String sectionTitle, String extractedUsername);

  @Query(
    value = "SELECT ts FROM Section ts WHERE ts.title = :sectionTitle " +
    "AND ts.deletedDate IS NULL " +
    "AND ts.createdBy = :extractedUsername "
  )
  Optional<Section> findSection(String sectionTitle, String extractedUsername);
}
