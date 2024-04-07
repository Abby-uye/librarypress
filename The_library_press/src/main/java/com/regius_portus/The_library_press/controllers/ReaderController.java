package com.regius_portus.The_library_press.controllers;

import com.regius_portus.The_library_press.dtos.request.*;
import com.regius_portus.The_library_press.dtos.response.CreateReaderResponse;
import com.regius_portus.The_library_press.exceptions.LibraryPressException;
import com.regius_portus.The_library_press.exceptions.ReaderExistException;
import com.regius_portus.The_library_press.services.ReaderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/the-library-app/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ReaderController {
    private ReaderService readerService;
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody CreateReaderRequest request) {
        try {
            CreateReaderResponse response = readerService.createReader(request);
            return ResponseEntity.ok().body(response);

        } catch (ReaderExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("search-book/{readerId}")
    public ResponseEntity<?> searchBook(@RequestBody SearchBookRequest request, @PathVariable("readerId") Long readerId) throws IOException, LibraryPressException, ReaderExistException {
        return ResponseEntity.ok().body(readerService.searchBook(readerId,request));
}
    @GetMapping("get-reading-list")
    public ResponseEntity<?> getAllBooks( @RequestBody GetReadingListRequest request) throws ReaderExistException, LibraryPressException {
    return ResponseEntity.ok().body(readerService.getAllBooks(request));
    }
    @PostMapping("login")
    public ResponseEntity<?> login(ReaderLoginRequest request){
        try {
            ReaderLoginResponse response = readerService.login(request);
            return ResponseEntity.ok().body(response);
        }
        catch (ReaderExistException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
