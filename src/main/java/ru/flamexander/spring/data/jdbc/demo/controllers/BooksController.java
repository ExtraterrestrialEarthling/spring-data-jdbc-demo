package ru.flamexander.spring.data.jdbc.demo.controllers;

import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.flamexander.spring.data.jdbc.demo.dtos.*;
import ru.flamexander.spring.data.jdbc.demo.exceptions.GlobalExceptionHandler;
import ru.flamexander.spring.data.jdbc.demo.exceptions.ResourceNotFoundException;
import ru.flamexander.spring.data.jdbc.demo.services.BooksService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BooksController {
    private final BooksService booksService;
    private final GlobalExceptionHandler globalExceptionHandler;

    @Autowired
    public BooksController(BooksService booksService, GlobalExceptionHandler globalExceptionHandler) {
        this.booksService = booksService;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    @GetMapping
    public SimplestPageDto<DetailedBookDto> findAllDetailedBooks() {
        return new SimplestPageDto<>(booksService.findAllDetailedBooks());
    }

    @PatchMapping("/{id}/title")
    public void updateTitleById(@PathVariable Long id, @RequestParam String value) {
        booksService.updateTitleById(id, value);
    }

    @GetMapping("/paged")
    public PageDto<SimpleBookDto> getBooksOnPage
            (@RequestParam(defaultValue = "1") @Positive Integer pageNumber,
             @RequestParam(defaultValue = "10") @Positive Integer pageSize){
        return booksService.getBooksOnPage(pageNumber, pageSize);
    }

    @GetMapping("/paged-and-sorted")
    public PageableAndSortableDto<SimpleBookDto> getBooksOnPageSorted
            (@RequestParam(defaultValue = "1") @Positive Integer pageNumber,
             @RequestParam(defaultValue = "10") @Positive Integer pageSize,
             @RequestParam(defaultValue = "id") String sortField,
             @RequestParam(defaultValue = "asc") String sortOrder){
        return booksService.getBooksOnPageSorted(pageNumber, pageSize, sortField, sortOrder);
    }

    @GetMapping("/top10")
    public List<SimpleBookDto> getTop10Books(){
        return booksService.getTop10Books();
    }

    @ExceptionHandler
    public void catchResourceNotFoundException(ResourceNotFoundException e){
        globalExceptionHandler.catchResourceNotFoundException(e);
    }

}
