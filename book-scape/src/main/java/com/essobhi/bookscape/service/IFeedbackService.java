package com.essobhi.bookscape.service;

import com.essobhi.bookscape.dto.FeedbackDto;
import com.essobhi.bookscape.dto.PageResponse;
import org.springframework.security.core.Authentication;

public interface IFeedbackService {
    FeedbackDto save (FeedbackDto dto, Authentication connectedUser);
    PageResponse<FeedbackDto> findAllFeedbacksByBook(Integer bookId, int page, int size, Authentication connectedUser);
}
