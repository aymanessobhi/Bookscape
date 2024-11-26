package com.essobhi.bookscape.mapper;

import com.essobhi.bookscape.domain.Book;
import com.essobhi.bookscape.domain.BookTransactionHistory;
import com.essobhi.bookscape.dto.BookDto;
import com.essobhi.bookscape.dto.BorrowedBookDto;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {
    public Book toBook(BookDto dto) {
        return Book.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .authorName(dto.getAuthorName())
                .synopsis(dto.getSynopsis())
                .archived(false)
                .shareable(dto.isShareable())
                .build();
    }

    public BookDto toBookDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .rate(book.getRate())
                .archived(book.isArchived())
                .shareable(book.isShareable())
                .cover(FileUtils.readFileFromLocation(book.getBookCover()))
                .build();
    }

    public BorrowedBookDto toBorrowedBookResponse(BookTransactionHistory history) {
        return BorrowedBookDto.builder()
                .id(history.getBook().getId())
                .title(history.getBook().getTitle())
                .authorName(history.getBook().getAuthorName())
                .isbn(history.getBook().getIsbn())
                .rate(history.getBook().getRate())
                .returned(history.isReturned())
                .returnApproved(history.isReturnApproved())
                .build();
    }
}
