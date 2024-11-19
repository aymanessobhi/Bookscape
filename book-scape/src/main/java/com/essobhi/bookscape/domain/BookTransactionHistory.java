package com.essobhi.bookscape.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BS_BOOK_TRANS_HISTORY")
public class BookTransactionHistory extends BaseEntity<BookTransactionHistory>{
    //user relationship
    //book relationship

    private boolean returned;
    private boolean returnApproved;
}
