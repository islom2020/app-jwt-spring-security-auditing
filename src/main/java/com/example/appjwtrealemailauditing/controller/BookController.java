//package com.example.appjwtrealemailauditing.controller;
//
//import com.example.appjwtrealemailauditing.dto.ApiResponse;
//import com.example.appjwtrealemailauditing.dto.BookDto;
//import com.example.appjwtrealemailauditing.entity.Book;
//import com.example.appjwtrealemailauditing.repository.BookRepository;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("api/book")
//@RequiredArgsConstructor
//public class BookController {
//
//    private final BookRepository bookRepository;
//    private final ModelMapper modelMapper;
//
//    @PostMapping()
//    public HttpEntity<?> addBook(@RequestBody BookDto bookDto) {
//        Book book = modelMapper.map(bookDto, Book.class);
//        bookRepository.save(book);
//        return ResponseEntity.ok(new ApiResponse("Book added", true));
//    }
//
//    @GetMapping
//    public HttpEntity<?> getBookList() {
//        return ResponseEntity.ok(new ApiResponse("Books", true, bookRepository.findAll()));
//    }
//}
