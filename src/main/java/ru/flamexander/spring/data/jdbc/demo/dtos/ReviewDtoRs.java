package ru.flamexander.spring.data.jdbc.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDtoRs {
    private Long id;
    private String userName;
    private String text;
    private Integer rating;
    private LocalDateTime date;
    private Long bookId;
}
