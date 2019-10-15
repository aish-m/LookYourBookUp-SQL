package ADBproject.LookYourBookUp.Models;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    private String bibNum;
    private String title;
    private String subjects;
    private String typeDescription;

    public Book () {

    }

    public Book(String bib_num, String title, String subjects, String type) {
        this.bibNum = bib_num;
        this.title = title;
        this.subjects = subjects;
        this.typeDescription = type;
    }

    public String getBib_num() {
        return bibNum;
    }

    public String getTitle() {
        return title;
    }

    public void setBib_num(String bib_num) {
        this.bibNum = bib_num;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getType() {
        return typeDescription;
    }

    public void setType(String type) {
        this.typeDescription = type;
    }
}
