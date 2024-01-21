package ru.flamexander.spring.data.jdbc.demo.repositories;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.flamexander.spring.data.jdbc.demo.dtos.SimpleBookDto;
import ru.flamexander.spring.data.jdbc.demo.dtos.DetailedBookDto;
import ru.flamexander.spring.data.jdbc.demo.entities.Book;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BooksRepository extends ListCrudRepository<Book, Long> {
    @Query(
            "select b.id, b.title, b.genre, b.average_rating, a.full_name as author_name, bd.description from BOOKS b " +
                    " left join AUTHORS a on b.author_id = a.id " +
                    " left join BOOKS_DETAILS bd on bd.book_id = b.id"
    )
    List<DetailedBookDto> findAllDetailedBooks();

    @Query("update books set title = :title where id = :id")
    @Modifying
    void changeTitleById(Long id, String title);

    @Query("select title, id from books limit :pageSize offset (:pageNumber-1) * :pageSize")
    List<SimpleBookDto> getBooksOnPage(Integer pageNumber, Integer pageSize);

    @Modifying
    @Query("UPDATE books b SET b.average_rating = :averageRating WHERE b.id = :bookId")
    void updateAverageRating(Long bookId, BigDecimal averageRating);

    @Query("SELECT b.id, b.title, AVG(r.rating) AS average_rating " +
            "FROM books b " +
            "LEFT JOIN reviews r ON r.book_id = b.id " +
            "WHERE r.date >= CURRENT_DATE - INTERVAL '1' MONTH " +
            "GROUP BY b.id, b.title " +
            "ORDER BY average_rating DESC " +
            "LIMIT 10")
    List<SimpleBookDto> getTop10Books();

}