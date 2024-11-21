package com.essobhi.bookscape.service;

import com.essobhi.bookscape.dto.BookDto;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface IBookService {
    BookDto save(BookDto dto, Authentication connectedUser);
    BookDto findById(Integer bookId);
}
