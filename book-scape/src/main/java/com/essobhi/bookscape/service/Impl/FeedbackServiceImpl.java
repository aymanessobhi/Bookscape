package com.essobhi.bookscape.service.Impl;

import com.essobhi.bookscape.domain.Book;
import com.essobhi.bookscape.domain.Feedback;
import com.essobhi.bookscape.domain.User;
import com.essobhi.bookscape.dto.FeedbackDto;
import com.essobhi.bookscape.dto.PageResponse;
import com.essobhi.bookscape.exception.OperationNotPermittedException;
import com.essobhi.bookscape.mapper.FeedbackMapper;
import com.essobhi.bookscape.repository.BookRepository;
import com.essobhi.bookscape.repository.FeedbackRepository;
import com.essobhi.bookscape.service.IFeedbackService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements IFeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
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
        Feedback feedback = feedbackMapper.toFeedback(dto);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return feedbackMapper.toFeedbackDto(savedFeedback, user.getId());
    }

    @Override
    public PageResponse<FeedbackDto> findAllFeedbacksByBook(Integer bookId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size);
        User user = ((User) connectedUser.getPrincipal());
        Page<Feedback> feedbacks = feedbackRepository.findAllByBookId(bookId, pageable);
        List<FeedbackDto> feedbackResponses = feedbacks.stream()
                .map(f -> feedbackMapper.toFeedbackDto(f, user.getId()))
                .toList();
        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );
    }


}
