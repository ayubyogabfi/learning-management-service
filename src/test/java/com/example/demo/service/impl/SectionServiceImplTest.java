package com.example.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.entity.Section;
import com.example.demo.repository.SectionRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SectionServiceImpl.class})
@ExtendWith(SpringExtension.class)
class SectionServiceImplTest {
    @MockBean
    private SectionRepository sectionRepository;

    @Autowired
    private SectionServiceImpl sectionServiceImpl;

    @Test
    void testGetAllSections() {
        ArrayList<Section> sectionList = new ArrayList<>();
        when(sectionRepository.findAllByOrderByTitleAsc()).thenReturn(sectionList);
        List<Section> actualAllSections = sectionServiceImpl.getAllSections();
        assertSame(sectionList, actualAllSections);
        assertTrue(actualAllSections.isEmpty());
        verify(sectionRepository).findAllByOrderByTitleAsc();
    }
}

