package com.essobhi.bookscape.service.Impl;

import com.essobhi.bookscape.domain.Book;
import com.essobhi.bookscape.domain.Feedback;
import com.essobhi.bookscape.domain.User;
import com.essobhi.bookscape.dto.FeedbackDto;
import com.essobhi.bookscape.exception.OperationNotPermittedException;
import com.essobhi.bookscape.mapper.FeedbackMapper;
import com.essobhi.bookscape.repository.BookRepository;
import com.essobhi.bookscape.repository.FeedbackRepository;
import com.essobhi.bookscape.service.IFeedbackService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements IFeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final BookRepository bookRepository;
    private final FeedbackMapper feedBackMapper;
    @Override
    public FeedbackDto save(FeedbackDto dto, Authentication connectedUser) {
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("No book found with the ID:: "+dto.getBookId()));
        if(book.isArchived() || !book.isShareable()){
            throw new OperationNotPermittedException("You cannot give a feedback  for an archived or not shareable book");
        }

        User user = ((User) connectedUser.getPrincipal());
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You cannot borrow or return your own book");
        }
        Feedback feedback = feedBackMapper.toFeedback(dto);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return feedBackMapper.toFeedBackDto(savedFeedback, user.getId());
    }


}
