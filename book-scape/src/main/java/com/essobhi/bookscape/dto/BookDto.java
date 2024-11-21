package com.essobhi.bookscape.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class BookDto extends BaseDto<BookDto>{
    @NotNull(message = "101")
    @NotEmpty(message = "101")
    private String title;
    @NotNull(message = "102")
    @NotEmpty(message = "102")
    private String authorName;
    @NotNull(message = "103")
    @NotEmpty(message = "103")
    private String isbn;
    private String synopsis;
    private boolean shareable;
}
