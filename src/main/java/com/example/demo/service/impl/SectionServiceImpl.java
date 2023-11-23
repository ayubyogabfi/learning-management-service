package com.example.demo.service.impl;

import com.example.demo.entity.Section;
import com.example.demo.repository.SectionRepository;
import com.example.demo.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    public List<Section> getAllSections() {
        return sectionRepository.findAllByOrderByTitleAsc();
    }
}
