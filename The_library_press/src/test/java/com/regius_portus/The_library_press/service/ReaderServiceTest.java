package com.regius_portus.The_library_press.service;

import com.regius_portus.The_library_press.dtos.request.*;
import com.regius_portus.The_library_press.dtos.response.BookSearchResponse;
import com.regius_portus.The_library_press.dtos.response.CreateReaderResponse;
import com.regius_portus.The_library_press.dtos.response.GetReadingListResponse;
import com.regius_portus.The_library_press.exceptions.LibraryPressException;
import com.regius_portus.The_library_press.exceptions.ReaderExistException;
import com.regius_portus.The_library_press.services.ReaderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
public class ReaderServiceTest {
    @Autowired
    private ReaderService readerService;

    @Test
    public void testThatCanCreateReader() throws ReaderExistException {
        CreateReaderRequest request = new CreateReaderRequest();
        request.setName("Abby");
        request.setEmail("Abgmail");
        request.setPassword("password");
        CreateReaderResponse response = readerService.createReader(request);
        assertNotNull(response);
    }

    @Test
    public void testThatCabCanCreateReader2() throws ReaderExistException {
        CreateReaderRequest request = new CreateReaderRequest();
        request.setName("Abbigail");
        request.setEmail("email");
        request.setPassword("pass");
        CreateReaderResponse response = readerService.createReader(request);
        log.info("register response ->{}",response);
        assertNotNull(response);

    }
@Test
    public void testThatAReaderCanSearchForABook() throws IOException, LibraryPressException, ReaderExistException {
    SearchBookRequest request = new SearchBookRequest();
    request.setTitle("wizard of oz");
    BookSearchResponse response = readerService.searchBook(1L,request);
    assertNotNull(response);
}

@Test
public void testThatReaderCanLogin() throws ReaderExistException {
        ReaderLoginRequest request = new ReaderLoginRequest();
        request.setEmail("Abgmail");
        request.setPassword("password");
        ReaderLoginResponse response = new ReaderLoginResponse();
         response = readerService.login(request);
        assertNotNull(response);
        log.info("login response ->{}",response);

}

    @Test
    public void testThatIfReaderSearchForABookThatDoesNotExistExceptionIsThrown() {
        SearchBookRequest request = new SearchBookRequest();
        request.setTitle("idontexist");
        assertThrows(LibraryPressException.class, () -> readerService.searchBook(2L, request));
    }
@Test
    public void testThatAReaderCanSearchForAnotherBook() throws IOException, LibraryPressException, ReaderExistException {
    SearchBookRequest request = new SearchBookRequest();
    request.setTitle("Pride and Prejudice");
    BookSearchResponse response = readerService.searchBook(3L,request);
    assertNotNull(response);
    log.info("response ->{}",response);

}
@Test
    public void testThatReaderCanGetTheirReadingList() throws ReaderExistException, LibraryPressException {
        GetReadingListRequest request = new GetReadingListRequest();
        request.setReaderId(1L);
        GetReadingListResponse response = readerService.getAllBooks(request);
        assertNotNull(response);
        log.info("response ->{}",response);

}


}
