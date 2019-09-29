package ADBproject.LookYourBookUp.Repository;

import ADBproject.LookYourBookUp.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAll();

    List<Book> findByTitleContaining(String bookTitle);

    Book findByBibNum(String bibNum);

    @Query("SELECT type FROM Book group by type order by count(*) desc")
    List<String> getPopularTypes();

    List<Book> findByType(String bookType);

    List<Book> findByBibNumIn(List<String> bibNumbers);

    List<Book> findByTitleContainingAndType(String bookTitle, String bookType);

    List<Book> findByTypeAndBibNumIn (String bookType, List<String> bibNums);

    List<Book> findByTitleContainingAndBibNumIn(String bookTitle, List<String> bibNums);

    List<Book> findByTitleContainingAndTypeAndBibNumIn(String bookTitle, String bookType, List<String> bibNums);
}