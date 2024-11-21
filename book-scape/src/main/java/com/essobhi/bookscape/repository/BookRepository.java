package com.essobhi.bookscape.repository;

import com.essobhi.bookscape.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
