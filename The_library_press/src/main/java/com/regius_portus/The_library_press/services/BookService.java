package com.regius_portus.The_library_press.services;

import com.regius_portus.The_library_press.data.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    void saveBook(Optional<Book> book) ;

    Optional<Book> findBookById(String bookId);

    boolean findByTitle(String title);
    List<Book> getAllBooksByTitle(String title);

    Book findBookByTitle(String title);

}
