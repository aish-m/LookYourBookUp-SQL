package ADBproject.LookYourBookUp.Repository;

import ADBproject.LookYourBookUp.Models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByBibNum(String bibNum);

    @Query(value = "SELECT typeDescription FROM Book group by typeDescription order by count(*) desc")
    Page<String> getPopularTypes(PageRequest pageRequest);

    Page<Book> findByTitleContaining(String bookTitle, Pageable pageRequest);

    Page<Book> findByTypeDescription(String bookType, Pageable pageRequest);

    Page<Book> findByBibNumIn(List<String> bibNumbers, Pageable pageRequest);

    Page<Book> findByTitleContainingAndTypeDescription(String bookTitle, String bookType, Pageable pageRequest);

    Page<Book> findByTypeDescriptionAndBibNumIn (String bookType, List<String> bibNums, Pageable pageRequest);

    Page<Book> findByTitleContainingAndBibNumIn(String bookTitle, List<String> bibNums, Pageable pageRequest);

    Page<Book> findByTitleContainingAndTypeDescriptionAndBibNumIn(String bookTitle, String bookType, List<String> bibNums, Pageable pageRequest);

    @Query("select count(*) from Book")
    String getTotalBookCount();

    String countByTitleContaining(String bookTitle);

    String countByTypeDescription(String bookType);

    String countByBibNumIn(List<String> bibNumbers);

    String countByTitleContainingAndTypeDescription(String bookTitle, String bookType);

    String countByTypeDescriptionAndBibNumIn(String bookType, List<String> bibNums);

    String countByTitleContainingAndBibNumIn(String bookTitle, List<String> bibNums);

    String countByTitleContainingAndTypeDescriptionAndBibNumIn(String bookTitle, String bookType, List<String> bibNums);
}