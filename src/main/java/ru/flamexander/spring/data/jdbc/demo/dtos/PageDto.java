package ru.flamexander.spring.data.jdbc.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<T> {
    List<T> elements;
    Integer pageNumber;
    Integer pageSize;
    Integer numberOfPages;
    Long totalNumberOfElements;

}
