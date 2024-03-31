package com.regius_portus.The_library_press.services;

import com.regius_portus.The_library_press.data.models.Book;
import com.regius_portus.The_library_press.data.repositories.BookRepository;
import com.regius_portus.The_library_press.dtos.request.SaveBookRequest;
import com.regius_portus.The_library_press.dtos.response.BookSearchResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class TheLibraryPressBookService implements BookService{
    private final BookRepository bookRepository;

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }
}
