package com.example.tddspringboot;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;




@Data
public class BookRequest {

    @NotEmpty
    @Size(max=30)
    private String title;

    @NotEmpty
    @Size(max=30)
    private String isbn;

    @NotEmpty
    @Size(max=30)
    private String author;
}
