package ADBproject.LookYourBookUp.Controllers;

import ADBproject.LookYourBookUp.Exceptions.ForeignKeyConstraintException;
import ADBproject.LookYourBookUp.Exceptions.NoRatingsException;
import ADBproject.LookYourBookUp.Models.Review;
import ADBproject.LookYourBookUp.Repository.ReviewRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// This controller has methods for operations on book reviews. This includes getting all reviews for a book and
// inserting reviews for a particular book

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewsController {

    @Autowired
    private ReviewRepository reviewRepository;

    @RequestMapping("/get/{bibNum}")
    List<Review> getAllReviewsForBook(@PathVariable(value = "bibNum") String bibNum) {
        return reviewRepository.findByBibNum(bibNum);
    }

    @RequestMapping("/getRating/{bibNum}")
    Float getRatingForBook(@PathVariable(value = "bibNum") String bibNum) {
        Float result =  reviewRepository.findAverageRating(bibNum);
        if(result == null) throw new NoRatingsException("No ratings yet!");
        return result;
    }

    @PostMapping("/insert")
    Boolean insertReviewForBook(@Valid @RequestBody Review review) {
        try {
            Review result = reviewRepository.save(review);
            return result.getBibNum().equals(review.getBibNum())
                    && result.getRecommend().equals(review.getRecommend())
                    && result.getReviewDescription().equals(review.getReviewDescription())
                    && result.getReviewHeading().equals(review.getReviewHeading())
                    && result.getReviewId() == review.getReviewId()
                    && result.getReviewRating() == review.getReviewRating()
                    && result.getUserId().equals(review.getUserId());
        } catch (Exception e) {
            Throwable t = e.getCause();
            while ((t != null) && !(t instanceof ConstraintViolationException)) {
                t = t.getCause();
            }
            if (t instanceof ConstraintViolationException) {
                // Here you're sure you have a ConstraintViolationException, you can handle it
                throw new ForeignKeyConstraintException("UserId or BibNum is invalid! Foreign key violation at the database! Insert unsuccessful!");
            }
            return false;
        }
    }
}
