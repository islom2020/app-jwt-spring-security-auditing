package com.example.appjwtrealemailauditing.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class BookDto {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private String author;

    @NotNull
    private Integer pageCount;
}
