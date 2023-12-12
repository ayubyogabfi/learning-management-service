package com.example.demo.repository;

import com.example.demo.entity.Article;
import com.example.demo.entity.Section;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
  List<Section> findSectionIdOnArticleSection(Long sectionId, String extractedUsername);

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

  @Query(
    value = "SELECT ts from Section ts WHERE ts.id = :sectionId " +
    "AND ts.createdBy = :extractedUsername AND ts.deletedDate IS NULL"
  )
  Section findSectionBySectionId(Long sectionId, String extractedUsername);

  @Transactional
  @Modifying
  @Query(
    value = "UPDATE Section ts SET ts.deletedDate = CURRENT_TIMESTAMP, ts.updatedBy = :extractedUsername " +
    "WHERE ts.id = :sectionId AND ts.deletedDate IS NULL " +
    "AND ts.createdBy = :extractedUsername"
  )
  void deleteSection(@Param("sectionId") Long articleId, @Param("extractedUsername") String extractedUsername);
}
