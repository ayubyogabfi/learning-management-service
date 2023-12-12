package com.example.demo.service;

import com.example.demo.dto.DeleteSectionRequest;
import com.example.demo.dto.DeleteSectionResponse;
import com.example.demo.entity.Section;
import java.util.List;

public interface SectionService {
  List<Section> getAllSections(String extractedUsername);
  DeleteSectionResponse deleteSection(DeleteSectionRequest request, String extractedUsername);
}
