package com.regius_portus.The_library_press.utils;

import org.springframework.http.HttpStatus;

public class GenerateApiResponse {
    public static  ApiResponse validationError(Object data) {
        return
                ApiResponse.builder()
                        .data(data)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .isSuccessful(false)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build();

    }
}
