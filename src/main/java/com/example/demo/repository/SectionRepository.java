package com.example.demo.repository;

import com.example.demo.entity.Section;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
  List<Section> findAllByOrderByTitleAsc();

  Optional<Section> findSectionBySectionId(String sectionId);
}
