package com.regius_portus.The_library_press.services;

import com.regius_portus.The_library_press.data.models.Book;
import com.regius_portus.The_library_press.data.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class TheLibraryPressBookService implements BookService{
    private final BookRepository bookRepository;

    @Override
    public void saveBook(Optional<Book> book) {
        book.ifPresent(bookRepository::save);
    }

    @Override
    public Optional<Book> findBookById(String bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public boolean findByTitle(String title) {
        return !bookRepository.findByTitleIgnoreCase(title).isEmpty();
    }

    @Override
    public List<Book> getAllBooksByTitle(String title) {
        return bookRepository.findByTitleIgnoreCase(title);
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findBookByTitleIgnoreCase(title);
    }
}
