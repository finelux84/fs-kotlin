package com.fishbutcher.fskotlin.review

import org.springframework.stereotype.Service

@Service
class ReviewService {
    val reviewRepository: ReviewRepository

    constructor(reviewRepository: ReviewRepository) {
        this.reviewRepository = reviewRepository
    }

    fun writeReview(restaurantId: Long, visitId: Long, comment: String, rating: Int): Long {
        val review = Review.of(visitId, comment, rating, restaurantId)
        val reviewId = reviewRepository.save(review).id!!
        return reviewId
    }

}