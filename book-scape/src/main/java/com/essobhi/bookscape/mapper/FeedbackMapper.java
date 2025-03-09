package com.essobhi.bookscape.mapper;


import com.essobhi.bookscape.domain.Book;
import com.essobhi.bookscape.domain.Feedback;
import com.essobhi.bookscape.dto.FeedbackDto;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {
    public Feedback toFeedback(FeedbackDto dto){
        return  Feedback.builder()
                .note(dto.getNote())
                .book(Book.builder()
                        .id(dto.getBookId())
                        .archived(false)
                        .shareable(false)
                        .build()
                )
                .build();
    }

    public FeedbackDto toFeedbackDto(Feedback feedBack, Integer id){
        return FeedbackDto.builder()
                .note(feedBack.getNote())
                .comment(feedBack.getComment())
                .ownFeedback(Objects.equals(feedBack.getCreatedBy(), id))
                .build();
    }
}
