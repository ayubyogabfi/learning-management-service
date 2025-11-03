package com.example.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.DeleteSectionRequest;
import com.example.demo.entity.Section;
import com.example.demo.repository.ArticleSectionRepository;
import com.example.demo.repository.SectionRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SectionServiceImpl.class})
@ExtendWith(SpringExtension.class)
class SectionServiceImplTest {
    @MockBean
    private ArticleSectionRepository articleSectionRepository;

    @MockBean
    private SectionRepository sectionRepository;

    @Autowired
    private SectionServiceImpl sectionServiceImpl;

    /**
     * Method under test: {@link SectionServiceImpl#getAllSections(String)}
     */
    @Test
    void testGetAllSections() {
        ArrayList<Section> sectionList = new ArrayList<>();
        when(sectionRepository.findAllByTitleOrderAscAndUserLogin(Mockito.<String>any())).thenReturn(sectionList);
        List<Section> actualAllSections = sectionServiceImpl.getAllSections("janedoe");
        assertSame(sectionList, actualAllSections);
        assertTrue(actualAllSections.isEmpty());
        verify(sectionRepository).findAllByTitleOrderAscAndUserLogin(Mockito.<String>any());
    }

    /**
     * Method under test: {@link SectionServiceImpl#deleteSection(DeleteSectionRequest, String)}
     */
    @Test
    void testDeleteSection() {
        doNothing().when(articleSectionRepository)
                .deleteArticleSectionBySectionId(Mockito.<Long>any(), Mockito.<String>any());

        Section section = new Section();
        section.setCreatedBy("Nov 11, 2023 8:00am GMT+0100");
        section.setCreatedDate(LocalDate.of(2023, 11, 11).atStartOfDay().atZone(ZoneOffset.UTC));
        section.setCreatedFrom("testemail@gmail.com");
        section.setDeletedDate(LocalDate.of(2023, 11, 11).atStartOfDay().atZone(ZoneOffset.UTC));
        section.setId(1L);
        section.setTitle("Section Title");
        section.setUpdatedBy("2023-11-11");
        section.setUpdatedDate(LocalDate.of(2023, 11, 11).atStartOfDay().atZone(ZoneOffset.UTC));
        section.setUpdatedFrom("2023-11-11");
        doNothing().when(sectionRepository).deleteSection(Mockito.<Long>any(), Mockito.<String>any());
        when(sectionRepository.findSectionBySectionId(Mockito.<Long>any(), Mockito.<String>any())).thenReturn(section);
        sectionServiceImpl.deleteSection(new DeleteSectionRequest(), "janedoe");
        verify(articleSectionRepository).deleteArticleSectionBySectionId(Mockito.<Long>any(), Mockito.<String>any());
        verify(sectionRepository).findSectionBySectionId(Mockito.<Long>any(), Mockito.<String>any());
        verify(sectionRepository).deleteSection(Mockito.<Long>any(), Mockito.<String>any());
    }
}

