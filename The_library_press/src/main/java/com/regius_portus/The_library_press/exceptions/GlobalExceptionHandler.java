package com.regius_portus.The_library_press.exceptions;

import com.regius_portus.The_library_press.dtos.response.BookSearchResponse;
import com.regius_portus.The_library_press.dtos.response.CreateReaderResponse;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LibraryPressException.class)
    public ResponseEntity<LibraryPressException> registerException(ReaderExistException readerExistException){
        return new  ResponseEntity<>(HttpStatusCode.valueOf(HttpStatus.SC_BAD_REQUEST));
    }

}
