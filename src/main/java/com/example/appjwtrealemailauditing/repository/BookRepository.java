package com.example.appjwtrealemailauditing.repository;

import com.example.appjwtrealemailauditing.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "book")
public interface BookRepository extends JpaRepository<Book, UUID> {

}