package com.regius_portus.The_library_press.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchBookRequest {
    private String title;
    private Long readerId;
}
