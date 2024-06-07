package com.regius_portus.The_library_press.utils;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class ApiResponse {
    private Object data;
    private HttpStatus httpStatus;
    private int statusCode;
    private boolean isSuccessful;

}
