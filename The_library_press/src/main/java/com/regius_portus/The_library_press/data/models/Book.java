package com.regius_portus.The_library_press.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private String id;
    private String title;
    @ElementCollection
    @Fetch(FetchMode.JOIN)
    private List<String> author;
    @ElementCollection
    @Fetch(FetchMode.JOIN)
    private List<String> formats;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_subjects",joinColumns = @JoinColumn(name = "book_id"))
    private List<String> subjects;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_bookshelves",joinColumns = @JoinColumn(name = "book_id"))
    private List<String> bookshelves ;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_languages",joinColumns = @JoinColumn(name = "book_id"))
    private List<String> languages ;



    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookId ='" + id + '\'' +
                ", title ='" + title + '\'' +
                ", author =" + author +
                ", formats =" + formats +
                ", subjects =" + subjects +
                ", bookshelves =" + bookshelves +
                ", languages =" + languages +
                '}';
    }
}
