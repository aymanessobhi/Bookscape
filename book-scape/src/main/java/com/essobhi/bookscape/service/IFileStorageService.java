package com.essobhi.bookscape.service;

import com.essobhi.bookscape.domain.Book;
import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    String saveFile(MultipartFile file, Integer id);
}
