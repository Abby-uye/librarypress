package com.regius_portus.The_library_press.dtos.response;

import com.regius_portus.The_library_press.data.models.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter

public class GetReadingListResponse {
    private List<Book> readingList;

    public String toString(){
        return "Reading list: {" + readingList + "}";
    }
}
