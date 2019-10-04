package ADBproject.LookYourBookUp.Controllers;

import ADBproject.LookYourBookUp.Exceptions.ForeignKeyConstraintException;
import ADBproject.LookYourBookUp.Models.BookCondition;
import ADBproject.LookYourBookUp.Models.ConditionReport;
import ADBproject.LookYourBookUp.Repository.ConditionRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

// This controller deals with all the operations related to book condition like getting conditions of all copies of a
// book, inserting condition of a book and running reports to know which books are in which condition

@RestController
@RequestMapping("/api/conditions")
@CrossOrigin(origins = "http://localhost:4200")
public class ConditionController {

    @Autowired
    private ConditionRepository conditionRepository;

    @RequestMapping("/get/{bibNum}")
    List<BookCondition> getConditionsByBibNum(@PathVariable(value = "bibNum") String bibNum) {
        return conditionRepository.findByBibNum(bibNum);
    }

    @PostMapping("/insert")
    Boolean inputBookCondition(@Valid @RequestBody BookCondition bookCondition) {
        try {
            BookCondition inserted = conditionRepository.save(bookCondition);
            return inserted.getBarcode().equals(bookCondition.getBarcode())
                    && inserted.getBibNum().equals(bookCondition.getBibNum())
                    && inserted.getBookCondition() == bookCondition.getBookCondition()
                    && inserted.getUserId().equals(bookCondition.getUserId());
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

    @RequestMapping("/getReport/{condition}")
    ConditionReport getAllBarcodesForCondition(@PathVariable(value = "condition") int condition) {
        List <BookCondition> bookConditions = conditionRepository.findByBookCondition(condition);
        int countOfBooks = conditionRepository.findReportCount(condition);
        return new ConditionReport(bookConditions, countOfBooks);
    }
}
