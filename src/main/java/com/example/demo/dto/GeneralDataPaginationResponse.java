package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.*;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralDataPaginationResponse<T> {

    public GeneralDataPaginationResponse(List<T> listOfData, Pageable pageable, long total, int nextPage) {
        var totalPages = pageable.getPageSize() == 0
                ? 1
                : (int) Math.ceil((double) total / (double) pageable.getPageSize());

        var pagination = new Pagination();
        pagination.setNextPage(nextPage);
        pagination.setTotalPage(totalPages);
        this.setPagination(pagination);

        this.setData(listOfData);
    }

    @JsonProperty(namespace = "pagination")
    private Pagination pagination;

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
}