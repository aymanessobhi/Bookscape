package com.essobhi.bookscape.service;

import com.essobhi.bookscape.dto.FeedbackDto;
import org.springframework.security.core.Authentication;

public interface IFeedbackService {
    FeedbackDto save (FeedbackDto dto, Authentication connectedUser);
}
