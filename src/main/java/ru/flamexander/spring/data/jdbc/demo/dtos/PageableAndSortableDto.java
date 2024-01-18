package ru.flamexander.spring.data.jdbc.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableAndSortableDto<T> {
    private List<T> elements;
    private int pageNumber;
    private int pageSize;
    private Integer numberOfPages;
    private Long totalNumberOfElements;

}
