package ru.flamexander.spring.data.jdbc.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.flamexander.spring.data.jdbc.demo.dtos.ReviewDtoRq;
import ru.flamexander.spring.data.jdbc.demo.dtos.ReviewDtoRs;
import ru.flamexander.spring.data.jdbc.demo.services.ReviewsService;

@RestController
    @RequestMapping("/api/v1/reviews")
    public class ReviewsController {
        private final ReviewsService reviewsService;

        @Autowired
        public ReviewsController(ReviewsService reviewsService) {
            this.reviewsService = reviewsService;
        }

        @PostMapping
        public void createNewReview(@RequestBody @Valid ReviewDtoRq reviewDtoRq) {
            reviewsService.createNewReview(reviewDtoRq);
        }

        @GetMapping("{reviewId}")
        public ReviewDtoRs getReviewById(@PathVariable Long reviewId){
            return reviewsService.getReviewById(reviewId);
        }
    }