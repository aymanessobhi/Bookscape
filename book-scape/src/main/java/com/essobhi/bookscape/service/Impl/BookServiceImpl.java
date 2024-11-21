package com.essobhi.bookscape.service.Impl;

import com.essobhi.bookscape.domain.Book;
import com.essobhi.bookscape.domain.User;
import com.essobhi.bookscape.dto.BookDto;
import com.essobhi.bookscape.mapper.BookMapper;
import com.essobhi.bookscape.repository.BookRepository;
import com.essobhi.bookscape.service.IBookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements IBookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    @Override
    public BookDto save(BookDto dto, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookMapper.toEntity(dto);
        book.setOwner(user);
        Book savedBook =  bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public BookDto findById(Integer bookId) {
        return bookMapper.toDto(bookRepository.findById(bookId)
                .orElseThrow(()-> new EntityNotFoundException("No book found  with the ID ::"+bookId)));
    }






}
