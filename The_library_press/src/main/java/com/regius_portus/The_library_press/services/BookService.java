package com.regius_portus.The_library_press.services;

import com.regius_portus.The_library_press.data.models.Book;
import com.regius_portus.The_library_press.dtos.request.SaveBookRequest;
import com.regius_portus.The_library_press.dtos.response.BookSearchResponse;

import java.io.IOException;

public interface BookService {

    void saveBook(Book book) ;
}
