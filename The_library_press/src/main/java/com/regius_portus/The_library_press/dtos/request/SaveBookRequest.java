package com.regius_portus.The_library_press.dtos.request;

import com.regius_portus.The_library_press.data.models.Book;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveBookRequest {
    private Book book;

}
