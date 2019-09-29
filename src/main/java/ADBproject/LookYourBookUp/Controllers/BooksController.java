package ADBproject.LookYourBookUp.Controllers;

import ADBproject.LookYourBookUp.Exceptions.ResourceNotFoundException;
import ADBproject.LookYourBookUp.Models.Book;
import ADBproject.LookYourBookUp.Repository.BookRepository;
import ADBproject.LookYourBookUp.Repository.ConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ConditionRepository conditionRepository;

    @GetMapping("/")
    List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/getTypes")
    List<String> getPopularBookTypes() {
        return bookRepository.getPopularTypes();
    }

    @GetMapping("/getDetails/{bibNum}")
    Book getBookDetailsByBibNum(@PathVariable(value = "bibNum") String bibNum) {
        Book result = bookRepository.findByBibNum(bibNum);
        if(result == null) throw new ResourceNotFoundException("Book", "bibNum", bibNum);
        return result;
    }

    @GetMapping("/filterBooks")
    List<Book> filterBooks(@RequestParam String bookTitle, @RequestParam String bookType, @RequestParam int bookCondition) {
        if(!bookTitle.isEmpty() && bookType.isEmpty() & bookCondition == 0)
            return bookRepository.findByTitleContaining(bookTitle);
        else if (bookTitle.isEmpty() && !bookType.isEmpty() & bookCondition == 0)
            return bookRepository.findByType(bookType);
        else if (bookTitle.isEmpty() && bookType.isEmpty() & bookCondition != 0) {
            List<String> bibNumbers = conditionRepository.findBooksWithCondition(bookCondition);
            return bookRepository.findByBibNumIn(bibNumbers);
        }
        else if (!bookTitle.isEmpty() && !bookType.isEmpty() & bookCondition == 0)
            return bookRepository.findByTitleContainingAndType(bookTitle, bookType);
        else if (bookTitle.isEmpty() && !bookType.isEmpty() & bookCondition != 0) {
            List<String> bibNumbers = conditionRepository.findBooksWithCondition(bookCondition);
            return bookRepository.findByTypeAndBibNumIn(bookType, bibNumbers);
        }
        else if (!bookTitle.isEmpty() && bookType.isEmpty()) {
            List<String> bibNumbers = conditionRepository.findBooksWithCondition(bookCondition);
            return bookRepository.findByTitleContainingAndBibNumIn(bookTitle, bibNumbers);
        }
        else  {
            List<String> bibNumbers = conditionRepository.findBooksWithCondition(bookCondition);
            return bookRepository.findByTitleContainingAndTypeAndBibNumIn(bookTitle, bookType, bibNumbers);
        }
    }
}
