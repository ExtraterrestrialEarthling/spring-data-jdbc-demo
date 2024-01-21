package ru.flamexander.spring.data.jdbc.demo.repositories;

import org.springframework.data.repository.ListPagingAndSortingRepository;
import ru.flamexander.spring.data.jdbc.demo.entities.Book;


public interface PagingAndSortingBooksRepository extends ListPagingAndSortingRepository<Book, Long> {
}
