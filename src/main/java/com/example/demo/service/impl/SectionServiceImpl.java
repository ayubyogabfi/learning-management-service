package com.example.demo.service.impl;

import com.example.demo.constants.AppConstants;
import com.example.demo.dto.DeleteSectionRequest;
import com.example.demo.dto.DeleteSectionResponse;
import com.example.demo.entity.Section;
import com.example.demo.repository.ArticleSectionRepository;
import com.example.demo.repository.SectionRepository;
import com.example.demo.service.SectionService;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectionServiceImpl implements SectionService {

  @Autowired
  private SectionRepository sectionRepository;

  @Autowired
  private ArticleSectionRepository articleSectionRepository;

  @Override
  public List<Section> getAllSections(String extractedUsername) {
    return sectionRepository.findAllByTitleOrderAscAndUserLogin(extractedUsername);
  }

  @Override
  public DeleteSectionResponse deleteSection(DeleteSectionRequest request, String extractedUsername) {
    Long sectionId = request.getSectionId();
    Section sections = sectionRepository.findSectionBySectionId(sectionId, extractedUsername);

    if (sections == null) {
      throw new InternalError("No Section To Delete");
    }
    String sectionTitle = sections.getTitle();

    // soft delete section on db
    sectionRepository.deleteSection(sectionId, extractedUsername);

    // soft delete article section which contains section id
    articleSectionRepository.deleteArticleSectionBySectionId(sectionId, extractedUsername);

    return DeleteSectionResponse
      .builder()
      .sectionTitle(sectionTitle)
      .message("Section Successfully Deleted")
      .deletedDate(ZonedDateTime.now(AppConstants.ZONE_ID))
      .build();
  }
}
