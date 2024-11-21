package com.essobhi.bookscape.mapper;

import com.essobhi.bookscape.domain.Book;
import com.essobhi.bookscape.dto.BookDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper extends BaseMapper<Book, BookDto>{
    public BookMapper(ModelMapper modelMapper, Class<Book> entityClass, Class<BookDto> dtoClass) {
        super(modelMapper, entityClass, dtoClass);
    }
}
