package ADBproject.LookYourBookUp.Controllers;

import ADBproject.LookYourBookUp.Exceptions.ResourceNotFoundException;
import ADBproject.LookYourBookUp.Models.Book;
import ADBproject.LookYourBookUp.Repository.BookRepository;
import ADBproject.LookYourBookUp.Repository.ConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Books controller to perform all operations related to books like getting books, getting books by their ID and
// filtering books based on various attributes

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:4200")
public class BooksController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ConditionRepository conditionRepository;

    @GetMapping("/{pageNumber}")
    List<Book> getAllBooks(@PathVariable(value = "pageNumber") int pageNumber) {
        return bookRepository.findAll(PageRequest.of(pageNumber-1, 10)).getContent();
    }

    @GetMapping("/getTypes")
    List<String> getPopularBookTypes() {
        return bookRepository.getPopularTypes(PageRequest.of(0, 10)).getContent();
    }

    @GetMapping("/getDetails/{bibNum}")
    Book getBookDetailsByBibNum(@PathVariable(value = "bibNum") String bibNum) {
        Book result = bookRepository.findByBibNum(bibNum);
        if(result == null) throw new ResourceNotFoundException("Book", "bibNum", bibNum);
        return result;
    }

    @GetMapping("/filterBooks")
    List<Book> filterBooks(@RequestParam String bookTitle, @RequestParam String bookType, @RequestParam int bookCondition, @RequestParam int pageNumber) {
        if(bookTitle.isEmpty() && bookType.isEmpty() & bookCondition == 0)
            return getAllBooks(pageNumber);
        else if(!bookTitle.isEmpty() && bookType.isEmpty() & bookCondition == 0)
            return bookRepository
                    .findByTitleContaining(bookTitle, PageRequest.of(pageNumber-1, 10))
                    .getContent();
        else if (bookTitle.isEmpty() && !bookType.isEmpty() & bookCondition == 0)
            return bookRepository
                    .findByTypeDescription(bookType, PageRequest.of(pageNumber-1, 10))
                    .getContent();
        else if (bookTitle.isEmpty() && bookType.isEmpty() & bookCondition != 0) {
            List<String> bibNumbers = conditionRepository.findBooksWithCondition(bookCondition);
            return bookRepository
                    .findByBibNumIn(bibNumbers, PageRequest.of(pageNumber-1, 10))
                    .getContent();
        }
        else if (!bookTitle.isEmpty() && !bookType.isEmpty() & bookCondition == 0)
            return bookRepository
                    .findByTitleContainingAndTypeDescription(bookTitle, bookType, PageRequest.of(pageNumber-1, 10))
                    .getContent();
        else if (bookTitle.isEmpty() && !bookType.isEmpty() & bookCondition != 0) {
            List<String> bibNumbers = conditionRepository.findBooksWithCondition(bookCondition);
            return bookRepository
                    .findByTypeDescriptionAndBibNumIn(bookType, bibNumbers, PageRequest.of(pageNumber-1, 10))
                    .getContent();
        }
        else if (!bookTitle.isEmpty() && bookType.isEmpty()) {
            List<String> bibNumbers = conditionRepository.findBooksWithCondition(bookCondition);
            return bookRepository
                    .findByTitleContainingAndBibNumIn(bookTitle, bibNumbers, PageRequest.of(pageNumber-1, 10))
                    .getContent();
        }
        else  {
            List<String> bibNumbers = conditionRepository.findBooksWithCondition(bookCondition);
            return bookRepository
                    .findByTitleContainingAndTypeDescriptionAndBibNumIn(bookTitle, bookType, bibNumbers, PageRequest.of(pageNumber-1, 10))
                    .getContent();
        }
    }

    @GetMapping("/filterBooksCount")
    Long getFilteredCount(@RequestParam String bookTitle, @RequestParam String bookType, @RequestParam int bookCondition) {
        if(!bookTitle.isEmpty() && bookType.isEmpty() & bookCondition == 0)
            return Long.parseLong(bookRepository.countByTitleContaining(bookTitle));
        else if (bookTitle.isEmpty() && !bookType.isEmpty() & bookCondition == 0)
            return Long.parseLong(bookRepository.countByTypeDescription(bookType));
        else if (bookTitle.isEmpty() && bookType.isEmpty() & bookCondition != 0) {
            List<String> bibNumbers = conditionRepository.findBooksWithCondition(bookCondition);
            return Long.parseLong(bookRepository.countByBibNumIn(bibNumbers));
        }
        else if (!bookTitle.isEmpty() && !bookType.isEmpty() & bookCondition == 0)
            return Long.parseLong(bookRepository.countByTitleContainingAndTypeDescription(bookTitle, bookType));
        else if (bookTitle.isEmpty() && !bookType.isEmpty() & bookCondition != 0) {
            List<String> bibNumbers = conditionRepository.findBooksWithCondition(bookCondition);
            return Long.parseLong(bookRepository.countByTypeDescriptionAndBibNumIn(bookType, bibNumbers));
        }
        else if (!bookTitle.isEmpty() && bookType.isEmpty()) {
            List<String> bibNumbers = conditionRepository.findBooksWithCondition(bookCondition);
            return Long.parseLong(bookRepository.countByTitleContainingAndBibNumIn(bookTitle, bibNumbers));
        }
        else  {
            List<String> bibNumbers = conditionRepository.findBooksWithCondition(bookCondition);
            return Long.parseLong(bookRepository.countByTitleContainingAndTypeDescriptionAndBibNumIn(bookTitle, bookType, bibNumbers));
        }
    }


    @GetMapping("/getTotalPageCount")
    Long getTotalNumberOfPages() {
        Long countOfBooks = Long.parseLong(bookRepository.getTotalBookCount());
        return (countOfBooks % 10 == 0) ? countOfBooks/10 : countOfBooks/10 + 1;
    }

    @GetMapping("/getTotalBookCount")
    Long getTotalNumberOfBooks() {
        return Long.parseLong(bookRepository.getTotalBookCount());
    }
}
