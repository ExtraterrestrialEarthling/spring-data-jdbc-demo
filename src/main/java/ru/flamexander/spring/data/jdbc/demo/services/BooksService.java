package ru.flamexander.spring.data.jdbc.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.flamexander.spring.data.jdbc.demo.dtos.*;
import ru.flamexander.spring.data.jdbc.demo.entities.Book;
import ru.flamexander.spring.data.jdbc.demo.repositories.BooksRepository;
import ru.flamexander.spring.data.jdbc.demo.repositories.PagingAndSortingBooksRepository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class BooksService {
    private final BooksRepository booksRepository;
    private final PagingAndSortingBooksRepository pagingAndSortingBooksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PagingAndSortingBooksRepository pagingAndSortingBooksRepository) {
        this.booksRepository = booksRepository;
        this.pagingAndSortingBooksRepository = pagingAndSortingBooksRepository;
    }

    public List<DetailedBookDto> findAllDetailedBooks() {
        return booksRepository.findAllDetailedBooks();
    }

    public void updateTitleById(Long id, String newTitle) {
        booksRepository.changeTitleById(id, newTitle);
    }

    public PageDto<SimpleBookDto> getBooksOnPage(Integer pageNumber, Integer pageSize) {
        List<SimpleBookDto> books = booksRepository.getBooksOnPage(pageNumber, pageSize);
        var pageDto = new PageDto<SimpleBookDto>();
        pageDto.setElements(books);
        pageDto.setPageNumber(pageNumber);
        pageDto.setPageSize(pageSize);
        long totalNumberOfBooks = booksRepository.count();
        pageDto.setTotalNumberOfElements(totalNumberOfBooks);
        pageDto.setNumberOfPages((int) (Math.ceil(totalNumberOfBooks / (double) pageSize)));
        if (pageNumber > pageDto.getNumberOfPages() - 1) {
            throw new IllegalArgumentException("Такой страницы не существует. " +
                    "Максимальное количество страниц: " + pageDto.getNumberOfPages());
        }
        return pageDto;
    }

    public PageableAndSortableDto<SimpleBookDto> getBooksOnPageSorted(Integer pageNumber,
                                                                      Integer pageSize,
                                                                      String sortField,
                                                                      String sortOrder) {
        pageNumber--; //PagingAndSortingRepository начинает считать страницы с 0, нам это не нужно
        List<String> allowedFields = getAllowedFields(SimpleBookDto.class);
        if (!allowedFields.contains(sortField)) {
            throw new IllegalArgumentException("Неверное поле для сортировки. Разрешенные поля: "
                    + allowedFields);
        }
        if (!"asc".equalsIgnoreCase(sortOrder) && !"desc".equalsIgnoreCase(sortOrder)) {
            throw new IllegalArgumentException("Неверно указано направление сортировки. " +
                    "Должно быть 'asc' или 'desc'.");
        }
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize,
                Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var pageDto = new PageableAndSortableDto<SimpleBookDto>();
        long totalNumberOfBooks = booksRepository.count();
        pageDto.setTotalNumberOfElements(totalNumberOfBooks);
        pageDto.setPageNumber(pageNumber);
        pageDto.setPageSize(pageSize);
        var page = pagingAndSortingBooksRepository.findAll(pageRequest);
        if (pageNumber > page.getTotalPages() - 1) {
            throw new IllegalArgumentException("Такой страницы не существует. " +
                    "Максимальное количество страниц: " + page.getTotalPages());
        }
        List<SimpleBookDto> bookDtos = new ArrayList<>();
        for (Book book : page.getContent()) {
            bookDtos.add(new SimpleBookDto(book.getId(), book.getTitle(), book.getAverageRating()));
        }
        pageDto.setElements(bookDtos);
        pageDto.setNumberOfPages(page.getTotalPages());
        return pageDto;

    }

    public List<SimpleBookDto> getTop10Books() {
        return booksRepository.getTop10Books();
    }

    private List<String> getAllowedFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                .map(Field::getName)
                .toList();
    }
}
