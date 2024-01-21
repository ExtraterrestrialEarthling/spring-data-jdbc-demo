package ru.flamexander.spring.data.jdbc.demo.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import ru.flamexander.spring.data.jdbc.demo.entities.Review;

import java.util.List;

public interface ReviewsRepository extends ListCrudRepository<Review, Long> {

    @Query("SELECT * FROM reviews WHERE book_id = :bookId")
    List<Review> findByBookId(@Param("bookId") Long bookId);
}
