package com.essobhi.bookscape.service.Impl;

import com.essobhi.bookscape.domain.Book;
import com.essobhi.bookscape.domain.BookTransactionHistory;
import com.essobhi.bookscape.domain.User;
import com.essobhi.bookscape.dto.BookDto;
import com.essobhi.bookscape.dto.BorrowedBookDto;
import com.essobhi.bookscape.dto.PageResponse;
import com.essobhi.bookscape.exception.OperationNotPermittedException;
import com.essobhi.bookscape.mapper.BookMapper;
import com.essobhi.bookscape.repository.BookRepository;
import com.essobhi.bookscape.repository.TransactionHistoryRepository;
import com.essobhi.bookscape.service.IBookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.essobhi.bookscape.specification.BookSpecification.withOwnerId;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements IBookService {
    private final TransactionHistoryRepository transactionHistoryRepository;
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

    @Override
    public PageResponse<BookDto> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
        List<BookDto> bookDto = books.stream()
                .map(book -> bookMapper.toDto(book))
                .collect(Collectors.toList());
        return new PageResponse<>(
                bookDto,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    @Override
    public PageResponse<BookDto> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(withOwnerId(connectedUser.getName()), pageable);
        List<BookDto> bookDto = books.stream()
                .map(book -> bookMapper.toDto(book))
                .collect(Collectors.toList());
        return new PageResponse<>(
                bookDto,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    @Override
    public PageResponse<BorrowedBookDto> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory>  allBorrowedBooks = transactionHistoryRepository.findAllBorrowedBooks(pageable, user.getId());
        List<BorrowedBookDto> bookDto = allBorrowedBooks.stream()
                .map(borrowedBook -> bookMapper.toDto(borrowedBook))
                .collect(Collectors.toList());
        return new PageResponse<>(
                bookDto,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );


    }

    @Override
    public PageResponse<BorrowedBookDto> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory>  allBorrowedBooks = transactionHistoryRepository.findAllReturnedBooks(pageable, user.getId());
        List<BorrowedBookDto> bookDto = allBorrowedBooks.stream()
                .map(borrowedBook -> bookMapper.toDto(borrowedBook))
                .collect(Collectors.toList());
        return new PageResponse<>(
                bookDto,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    @Override
    public Integer updateShareableStatus(int bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new EntityNotFoundException("No book found  with the ID:: "+ bookId));
        User user = ((User) connectedUser.getPrincipal());
        if(!Objects.equals(book.getOwner().getBooks(), user.getId())){
            throw new OperationNotPermittedException("You cannot update books shareable status");
        }
        book.setShareable(!book.isShareable());
        bookRepository.save(book);
        return bookId;
    }


}
