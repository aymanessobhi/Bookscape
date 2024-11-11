package com.essobhi.bookscape.domain;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="BS_TOKEN")
public class Token {
    @Id
    @GeneratedValue
    private Long id;
    private String token;
    @ManyToOne
    @JoinColumn(name="userId",nullable = false)
    private User user;
}
