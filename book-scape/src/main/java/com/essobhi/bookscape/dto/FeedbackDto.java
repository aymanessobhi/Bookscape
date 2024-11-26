package com.essobhi.bookscape.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {
    Double note;
    String comment;
    Integer bookId;
    Boolean ownFeedback;
}
