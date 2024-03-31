package com.regius_portus.The_library_press.dtos.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.regius_portus.The_library_press.data.models.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class BookSearchResponse {
    private List<Book> books;
}
