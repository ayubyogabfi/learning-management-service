package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralDataPaginationResponse<T> {

    public GeneralDataPaginationResponse(List<T> listOfData, Pageable pageable, long total, int nextPage, Filter filter, Sort sort) {
        var totalPages = pageable.getPageSize() == 0
                ? 1
                : (int) Math.ceil((double) total / (double) pageable.getPageSize());

        var pagination = new Pagination();
        pagination.setNextPage(nextPage);
        pagination.setTotalPage(totalPages);
        this.setPagination(pagination);

        this.setFilter(filter);
        this.setSort(sort);
        this.setData(listOfData);
    }

    @JsonProperty(namespace = "pagination")
    private Pagination pagination;

    @JsonProperty(namespace = "filter")
    private Filter filter;

    @JsonProperty(namespace = "sort")
    private Sort sort;

    @JsonProperty(namespace = "data")
    private List<T> data;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Pagination {

        @JsonProperty("total_page")
        private int totalPage;

        @JsonProperty("next_page")
        private int nextPage;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Filter {

        @JsonProperty("section_title")
        private String sectionTitle;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Sort {

        @JsonProperty("order")
        private String order = "asc";
    }
}