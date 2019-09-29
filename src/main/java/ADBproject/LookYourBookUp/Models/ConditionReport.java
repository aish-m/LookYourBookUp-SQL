package ADBproject.LookYourBookUp.Models;

import java.util.List;

public class ConditionReport {

    private List<BookCondition> bookConditions;
    private int count;

    public ConditionReport(List<BookCondition> bookConditions, int count) {
        this.bookConditions = bookConditions;
        this.count = count;
    }

    public List<BookCondition> getBookConditions() {
        return bookConditions;
    }

    public void setBookConditions(List<BookCondition> bookConditions) {
        this.bookConditions = bookConditions;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
