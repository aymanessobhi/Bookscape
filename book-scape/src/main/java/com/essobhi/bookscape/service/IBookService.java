package com.essobhi.bookscape.service;

import com.essobhi.bookscape.dto.BookDto;
import com.essobhi.bookscape.dto.BorrowedBookDto;
import com.essobhi.bookscape.dto.PageResponse;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface IBookService {
    BookDto save(BookDto dto, Authentication connectedUser);
    BookDto findById(Integer bookId);
    PageResponse<BookDto> findAllBooks(int page, int size, Authentication connectedUser);
    PageResponse<BookDto> findAllBooksByOwner(int page, int size, Authentication connectedUser);
    PageResponse<BorrowedBookDto> findAllBorrowedBooks(int page, int size, Authentication connectedUser);
}
