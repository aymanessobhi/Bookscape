package com.essobhi.bookscape.controller;


import com.essobhi.bookscape.dto.FeedbackDto;
import com.essobhi.bookscape.service.IFeedbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("feedbacks")
@RequiredArgsConstructor
@Tag(name="Book")
public class FeedbackController {
    private final IFeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackDto> saveFeedback(
            @Valid @RequestBody FeedbackDto dto,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(feedbackService.save(dto, connectedUser));
    }
}
