package ru.flamexander.spring.data.jdbc.demo.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDtoRq {
    @NotBlank
    private String userName;
    @NotBlank
    private String text;
    @PositiveOrZero
    @Max(value = 10, message = "Рейтинг не может быть выше 10.")
    private Integer rating;
    @NotNull
    private Long bookId;
}
