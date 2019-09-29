package ADBproject.LookYourBookUp.Repository;

import ADBproject.LookYourBookUp.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByBibNum(String bibNum);

    @Query("SELECT AVG(reviewRating) FROM Review WHERE bibNum = ?1")
    Float findAverageRating(String bibNum);
}
