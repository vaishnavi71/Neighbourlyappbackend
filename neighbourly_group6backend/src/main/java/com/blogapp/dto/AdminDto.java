package com.blogapp.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

 

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

 

    // getters and setters
}