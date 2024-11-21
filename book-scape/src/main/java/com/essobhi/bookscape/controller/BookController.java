package com.essobhi.bookscape.controller;

import com.essobhi.bookscape.dto.BookDto;
import com.essobhi.bookscape.service.IBookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name="Book")
public class BookController {

    private final IBookService bookService;
    @PostMapping
    public ResponseEntity<BookDto> saveBook(
            @Valid @RequestBody BookDto dto,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.save(dto, connectedUser));
    }
}
