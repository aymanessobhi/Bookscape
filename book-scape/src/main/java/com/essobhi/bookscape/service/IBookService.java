package com.essobhi.bookscape.service;

import com.essobhi.bookscape.dto.BookDto;
import org.springframework.security.core.Authentication;

public interface IBookService {
    BookDto save(BookDto dto, Authentication connectedUser);

}
