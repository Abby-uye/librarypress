package com.regius_portus.The_library_press.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateReaderRequest {
    private String name;
    private String email;

}
