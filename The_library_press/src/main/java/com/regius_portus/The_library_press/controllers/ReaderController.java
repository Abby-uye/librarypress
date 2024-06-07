package com.regius_portus.The_library_press.controllers;

import com.regius_portus.The_library_press.dtos.request.*;
import com.regius_portus.The_library_press.dtos.response.CreateReaderResponse;
import com.regius_portus.The_library_press.exceptions.LibraryPressException;
import com.regius_portus.The_library_press.exceptions.ReaderExistException;
import com.regius_portus.The_library_press.services.ReaderService;
import com.regius_portus.The_library_press.utils.ApiResponse;
import com.regius_portus.The_library_press.utils.GenerateApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/the-library-press/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ReaderController {
    private ReaderService readerService;
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid CreateReaderRequest request, BindingResult result) throws ReaderExistException {
        ApiResponse errorMessage = getApiResponseResponseEntity(result);
        if (errorMessage != null) return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok().body(readerService.createReader(request));


    }
    @PostMapping("search-book/{readerId}")
    public ResponseEntity<?> searchBook(@RequestBody @Valid SearchBookRequest request, BindingResult result, @PathVariable("readerId") Long readerId) throws IOException, LibraryPressException, ReaderExistException {
        ApiResponse errorMessage = getApiResponseResponseEntity(result);
        if (errorMessage != null) return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok().body(readerService.searchBook(readerId,request));
}
    @PostMapping("get-reading-list")
    public ResponseEntity<?> getAllBooks( @RequestBody @Valid GetReadingListRequest request,BindingResult result) throws ReaderExistException, LibraryPressException {
        ApiResponse errorMessage = getApiResponseResponseEntity(result);
        if (errorMessage != null) return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok().body(readerService.getAllBooks(request));
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid ReaderLoginRequest request,BindingResult result) throws ReaderExistException {
        ApiResponse errorMessage = getApiResponseResponseEntity(result);
        if (errorMessage != null) return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok().body(readerService.login(request));

    }


    private ApiResponse getApiResponseResponseEntity(BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation error(s): ");
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append("Field '")
                        .append(error.getField())
                        .append("' ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
            return GenerateApiResponse.validationError(errorMessage.toString());
        }
        return null;
    }
}
