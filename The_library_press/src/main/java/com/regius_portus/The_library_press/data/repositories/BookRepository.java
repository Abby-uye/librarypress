package com.regius_portus.The_library_press.data.repositories;

import com.regius_portus.The_library_press.data.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,String> {

    List<Book> findByTitleIgnoreCase(String title);

    Book findBookByTitleIgnoreCase(String title);
}
