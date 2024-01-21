package ru.flamexander.spring.data.jdbc.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table("REVIEWS")
public class Review {
    @Id
    private Long id;
    private String userName;
    private String text;
    private LocalDateTime date;
    private Integer rating;
    private Long bookId;
}
