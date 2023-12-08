package com.example.demo.service.impl;

import com.example.demo.entity.Section;
import com.example.demo.repository.SectionRepository;
import com.example.demo.service.SectionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectionServiceImpl implements SectionService {

  @Autowired
  private SectionRepository sectionRepository;

  @Override
  public List<Section> getAllSections(String extractedUsername) {
    return sectionRepository.findAllByTitleOrderAscAndUserLogin(extractedUsername);
  }
}
