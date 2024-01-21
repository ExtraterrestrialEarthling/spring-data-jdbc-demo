package ru.flamexander.spring.data.jdbc.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.flamexander.spring.data.jdbc.demo.dtos.*;
import ru.flamexander.spring.data.jdbc.demo.entities.Book;
import ru.flamexander.spring.data.jdbc.demo.entities.Review;
import ru.flamexander.spring.data.jdbc.demo.exceptions.ResourceNotFoundException;
import ru.flamexander.spring.data.jdbc.demo.repositories.BooksRepository;
import ru.flamexander.spring.data.jdbc.demo.repositories.ReviewsRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewsService {
    private final ReviewsRepository reviewsRepository;
    private final BooksRepository booksRepository;

    @Autowired
    public ReviewsService(ReviewsRepository reviewsRepository, BooksRepository booksRepository) {
        this.reviewsRepository = reviewsRepository;
        this.booksRepository = booksRepository;
    }

    public void createNewReview(ReviewDtoRq reviewDtoRq){
        Long bookId = reviewDtoRq.getBookId();
        Book book = booksRepository.findById(bookId).orElseThrow(()->
                new ResourceNotFoundException("Книга с ID, представленным в DTO объекте, не найдена"));

        Review newReview = new Review(null, reviewDtoRq.getUserName(), reviewDtoRq.getText(), LocalDateTime.now(),
                reviewDtoRq.getRating(), bookId);

        if(book.getAverageRating() == null){
            book.setAverageRating(new BigDecimal(reviewDtoRq.getRating()));
            booksRepository.updateAverageRating(book.getId(), book.getAverageRating());
        } else {
            List<Review> reviews = reviewsRepository.findByBookId(bookId);
            var avg = calculateAvgRating(reviews);
            booksRepository.updateAverageRating(bookId, avg);
        }

            reviewsRepository.save(newReview);
    }

    public ReviewDtoRs getReviewById(Long reviewId){
        Review review = reviewsRepository.findById(reviewId).orElseThrow(()->
                new ResourceNotFoundException("Отзыв с таким ID не найден"));
        return new ReviewDtoRs(review.getId(),review.getUserName(),
                review.getText(), review.getRating(), review.getDate(), review.getBookId());
        }

        private BigDecimal calculateAvgRating(List<Review> reviews){
        int sum = 0;
        for (Review review : reviews){
            sum+=review.getRating();
        }
        return reviews.size()==0 ? BigDecimal.ZERO : new BigDecimal(sum)
                .divide(new BigDecimal(reviews.size()), 2, RoundingMode.HALF_UP);
    }
}
