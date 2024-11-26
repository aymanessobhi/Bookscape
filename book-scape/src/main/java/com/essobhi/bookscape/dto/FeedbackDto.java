package com.essobhi.bookscape.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FeedbackDto {
    Double note;
    String comment;
    Integer bookId;
    Boolean ownFeedback;
}
