package com.al.ai.platform.core.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private Long total;
    private Integer totalPages;
    private List<T> list;
    private Boolean hasPrevious;
    private Boolean hasNext;

    public static <T> PageResult<T> of(List<T> list, Integer pageNum, Integer pageSize, Long total) {
        int totalPages = pageSize == 0 ? 0 : (int) Math.ceil((double) total / pageSize);
        return new PageResult<>(pageNum, pageSize, total, totalPages, list, pageNum > 1, pageNum < totalPages);
    }

    public static <T> PageResult<T> empty(Integer pageNum, Integer pageSize) {
        return new PageResult<>(pageNum, pageSize, 0L, 0, List.of(), false, false);
    }
}
