package ADBproject.LookYourBookUp.Repository;

import ADBproject.LookYourBookUp.Models.BookCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionRepository extends JpaRepository<BookCondition, Long> {

    List<BookCondition> findByBibNum(String bibNum);

    List<BookCondition> findByBookCondition(int condition);

    @Query("SELECT count(barcode) FROM BookCondition WHERE bookCondition = ?1")
    int findReportCount(int condition);

    @Query("SELECT DISTINCT bibNum FROM BookCondition WHERE bookCondition >= ?1")
    List<String> findBooksWithCondition (int condition);
}
