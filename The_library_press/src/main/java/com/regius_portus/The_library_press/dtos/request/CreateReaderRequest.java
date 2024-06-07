package com.regius_portus.The_library_press.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateReaderRequest {
    @NotBlank(message = "Required field")
    @Size(min = 3,max = 100,message = "the length of your name must be greater than 3 and less than 100")
    private String name;
    @NotBlank(message = "Required field")
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[a-zA-Z]{2,}$", message = "Please enter a valid email address.")
    private String email;
    @NotBlank(message = "Required field")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Please enter a valid password")
    private String password;

}
