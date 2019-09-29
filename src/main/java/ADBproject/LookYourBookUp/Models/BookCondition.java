package ADBproject.LookYourBookUp.Models;

import javax.persistence.*;

@Entity
@Table(name = "conditions")
public class BookCondition {
    @Id
    private String barcode;
    private String bibNum;
    private int bookCondition;
    private String userId;

    public BookCondition () {

    }

    public BookCondition(String barcode, String bibNum, int bookCondition, String userId) {
        this.barcode = barcode;
        this.bibNum = bibNum;
        this.bookCondition = bookCondition;
        this.userId = userId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(int bookCondition) {
        this.bookCondition = bookCondition;
    }

    public String getBibNum() {
        return bibNum;
    }

    public void setBibNum(String bibNum) {
        this.bibNum = bibNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
