package ru.flamexander.spring.data.jdbc.demo.dtos;

import java.math.BigDecimal;

public class SimpleBookDto {
    private Long id;
    private String title;
    private BigDecimal averageRating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public SimpleBookDto() {
    }

    public SimpleBookDto(Long id, String title, BigDecimal averageRating) {
        this.id = id;
        this.title = title;
        this.averageRating = averageRating;
    }
}
