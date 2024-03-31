package com.regius_portus.The_library_press.data.repositories;

import com.regius_portus.The_library_press.data.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,String> {
}
