package com.regius_portus.The_library_press.service;

import com.regius_portus.The_library_press.dtos.request.CreateReaderRequest;
import com.regius_portus.The_library_press.dtos.request.SearchBookRequest;
import com.regius_portus.The_library_press.dtos.response.BookSearchResponse;
import com.regius_portus.The_library_press.dtos.response.CreateReaderResponse;
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
        request.setEmail("Abemail");
        CreateReaderResponse response = readerService.createReader(request);
        assertNotNull(response);
    }

    @Test
    public void testThatCabCanCreateReader2() throws ReaderExistException {
        CreateReaderRequest request = new CreateReaderRequest();
        request.setName("Abbigail");
        request.setEmail("email");
        CreateReaderResponse response = readerService.createReader(request);
        log.info("register response ->{}",response);
        assertNotNull(response);

    }
@Test
    public void testThatAReaderCanSearchForABook() throws IOException, LibraryPressException, ReaderExistException {
    SearchBookRequest request = new SearchBookRequest();
    request.setTitle("romeo and juliet");
    BookSearchResponse response = readerService.searchBook(1L,request);
    assertNotNull(response);
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
    BookSearchResponse response = readerService.searchBook(1L,request);
    assertNotNull(response);
    log.info("response ->{}",response);

}
@Test
    public void testThatReaderCanGetTheirReadingList(){

}


}
