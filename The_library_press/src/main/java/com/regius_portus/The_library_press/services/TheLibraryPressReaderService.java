package com.regius_portus.The_library_press.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.regius_portus.The_library_press.data.models.Book;
import com.regius_portus.The_library_press.data.models.Reader;
import com.regius_portus.The_library_press.data.repositories.ReaderRepository;
import com.regius_portus.The_library_press.dtos.request.*;
import com.regius_portus.The_library_press.dtos.response.BookSearchResponse;
import com.regius_portus.The_library_press.dtos.response.CreateReaderResponse;
import com.regius_portus.The_library_press.dtos.response.GetReadingListResponse;
import com.regius_portus.The_library_press.exceptions.LibraryPressException;
import com.regius_portus.The_library_press.exceptions.ReaderExistException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Transactional
public class TheLibraryPressReaderService implements ReaderService {
    private final ModelMapper modelMapper;
    private final ReaderRepository readerRepository;
    private final BookService bookService;

    @Override
    public CreateReaderResponse createReader(CreateReaderRequest request) throws ReaderExistException {
        if (existingUser(request.getEmail())) throw new ReaderExistException("{\"err\": \"Reader with this details already exist\"}");
        Reader reader = modelMapper.map(request, Reader.class);
        readerRepository.save(reader);
        CreateReaderResponse response = new CreateReaderResponse();
        response.setReaderId(String.valueOf(reader.getId()));
        return response;
    }

    @Override
    public BookSearchResponse searchBook(Long id, SearchBookRequest request) throws IOException, LibraryPressException, ReaderExistException {
        String bookTitle = formatBookTitle(request.getTitle());
        String apiUrl = "https://gutendex.com/books?search=" + bookTitle;
        String jsonResponse = gutendexFetchBook(apiUrl);
        return getSearchBookResponse(id, jsonResponse);
    }

    @Override
    public GetReadingListResponse getAllBooks(GetReadingListRequest request) throws ReaderExistException, LibraryPressException {
        Reader reader = findBy(request.getReaderId());
        Hibernate.initialize(reader.getBooks());
        List<Book> allBooks = reader.getBooks();
        if (allBooks.isEmpty()){
            throw new LibraryPressException("\"err\" :\"You do not have a reading list yet, search for books that you like to add to your reading list\"");
        }
        GetReadingListResponse response = new GetReadingListResponse();
        response.setReadingList(reader.getBooks());
        return response;
    }

    @Override
    public ReaderLoginResponse login(ReaderLoginRequest request) throws ReaderExistException {
        if(!existingUser(request.getEmail())) throw new ReaderExistException("\"err\" :\"Hello there you are not a registered user, try registering instead\"");
        Reader foundReader = findBy(request.getEmail());
        if(foundReader.getPassword().equals(request.getPassword())){
            foundReader.setGrantAccess(true);
        }
        ReaderLoginResponse response = new ReaderLoginResponse();
        response.setMessage("Successfully Logged in");

        return response;
    }

    private BookSearchResponse getSearchBookResponse(Long id, String jsonResponse) throws LibraryPressException, ReaderExistException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
        try {
            root = mapper.readTree(jsonResponse);
        } catch (JsonProcessingException e) {
            throw new LibraryPressException("\"err\" :\"Error processing JSON response\"");
        }
        if (root.get("results") == null || root.get("results").isNull()) {
            throw new LibraryPressException("\"err\" :\"No books found with this title. Please double-check your spelling or try searching for a different book.\"");
        }
        return getSearchBook(id, root);
    }


    private BookSearchResponse getSearchBook(Long readerId, JsonNode root) throws LibraryPressException, ReaderExistException {
        JsonNode results = root.get("results");
        BookSearchResponse response = new BookSearchResponse();
        List<Book> books = new ArrayList<>();
        Reader reader = findBy(readerId);
        for (JsonNode bookNode : results) {
            if (bookNode == null || bookNode.isNull()) {
                throw new LibraryPressException("\"err\" :\"The requested book information is not available. Please double-check the book title and try again.\"");
            }

            String bookId = bookNode.get("id").asText();
            String bookTitle = bookNode.get("title").asText();
            List<String> authors = getAuthors(bookNode);

            List<String> subjects = getSubjects(bookNode);

            List<String> languages = getLanguages(bookNode);

            List<String> bookshelves = getBookShelves(bookNode);

            List<String> formats = getFormats(bookNode);

            Book book = getBook(bookId, bookTitle, authors, bookshelves, subjects, languages, formats);
            bookService.saveBook(book);
            books.add(book);
            reader.getBooks().add(book);
        }

        readerRepository.save(reader);
        response.setBooks(books);

        if (response.getBooks() == null || response.getBooks().isEmpty()) {
            throw new LibraryPressException("\"err\" :\"Sorry, no books matching your search query were found.\"");
        }
return response;
    }
    private static List<String> getBookShelves(JsonNode bookNode) {
        List<String> bookshelves = new ArrayList<>();
        for (JsonNode bookShelvesNode : bookNode.get("bookshelves")){
            bookshelves.add(bookShelvesNode.asText());
        }
        Hibernate.initialize(bookshelves);
        return bookshelves;
    }

    private Reader findBy(Long id) throws ReaderExistException {

        return readerRepository.findById(id).orElseThrow(() -> new ReaderExistException(String.format("\"err\" :\"reader with id %d not found\"", id)));
    }

    private static List<String> getLanguages(JsonNode bookNode) {
        List<String> languages = new ArrayList<>();
        for (JsonNode languageNode : bookNode.get("languages")) {
            languages.add(languageNode.asText());
        }
        Hibernate.initialize(languages);
        return languages;
    }

    private static List<String> getSubjects(JsonNode bookNode) {
        List<String> subjects = new ArrayList<>();
        for (JsonNode subjectNode : bookNode.get("subjects")) {
            subjects.add(subjectNode.asText());
        }
        Hibernate.initialize(subjects);
        return subjects;
    }

    private static List<String> getAuthors(JsonNode bookNode) {
        List<String> authors = new ArrayList<>();
        for (JsonNode authorNode : bookNode.get("authors")) {
            authors.add("Name: " + authorNode.get("name").asText());
            authors.add("Birth Year: " + authorNode.get("birth_year").asText());
            authors.add("Death Year: " + authorNode.get("death_year").asText());
        }
        Hibernate.initialize(authors);
        return authors;
    }

    public static List<String> getFormats(JsonNode bookNode) {
        List<String> formats = new ArrayList<>();
        JsonNode formatsNode = bookNode.get("formats");
        if (formatsNode != null) {
            formatsNode.fields().forEachRemaining(entry -> {
                formats.add(entry.getValue().asText());
            });
        }
        return formats;
    }

    private static Book getBook(String bookId, String bookTitle, List<String> authors, List<String> bookshelves, List<String> subjects, List<String> languages, List<String> formats) {
        Book book = new Book();
        book.setId(bookId);
        book.setTitle(bookTitle);
        book.setAuthor(authors);
        book.setBookshelves(bookshelves);
        book.setSubjects(subjects);
        book.setFormats(formats);
        book.setLanguages(languages);
        return book;
    }



        private String gutendexFetchBook(String apiUrl) throws IOException, LibraryPressException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(apiUrl);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    throw new LibraryPressException("\"err\" :\"Failed to fetch book: HTTP status code\" " + statusCode);
                }
                String jsonResponse = EntityUtils.toString(response.getEntity());
                if (jsonResponse.isEmpty()) {
                    throw new LibraryPressException("\"err\" :\"No response received from the server\"");
                }
                return jsonResponse;
            }
        }
    }




    private String formatBookTitle(String bookTitle) {
        return bookTitle.replace(" ", "%20");
    }

    private boolean existingUser(String email) {
        return readerRepository.findByEmail(email).isPresent();
    }
    private boolean existingUser(Long id){

        return readerRepository.findById(id).isPresent();
    }
private Reader findBy(String email){
    return readerRepository.findByEmail(email).get();


}
}
