package com.regius_portus.The_library_press.exceptions;

import com.regius_portus.The_library_press.dtos.response.BookSearchResponse;
import com.regius_portus.The_library_press.dtos.response.CreateReaderResponse;
import com.regius_portus.The_library_press.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ReaderExistException.class)
    public ResponseEntity<ApiResponse> readerExistException(ReaderExistException readerExistException){
        return new ResponseEntity<>(ApiResponse.builder()
                .data(readerExistException.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .isSuccessful(false)
                .build(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(LibraryPressException.class)
    public ResponseEntity<ApiResponse> libraryPressException(LibraryPressException libraryPressException){
        return new ResponseEntity<>(ApiResponse.builder()
                .data(libraryPressException.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .isSuccessful(false)
                .build(), HttpStatus.BAD_REQUEST);
    }
}
