package com.regius_portus.The_library_press.services;

import com.regius_portus.The_library_press.dtos.request.CreateReaderRequest;
import com.regius_portus.The_library_press.dtos.request.SearchBookRequest;
import com.regius_portus.The_library_press.dtos.response.BookSearchResponse;
import com.regius_portus.The_library_press.dtos.response.CreateReaderResponse;
import com.regius_portus.The_library_press.exceptions.LibraryPressException;
import com.regius_portus.The_library_press.exceptions.ReaderExistException;

import java.io.IOException;

public interface ReaderService {
    CreateReaderResponse createReader(CreateReaderRequest request) throws ReaderExistException;

    BookSearchResponse searchBook(Long id, SearchBookRequest request) throws IOException, LibraryPressException, ReaderExistException;
}
