import {Component, inject, OnInit} from '@angular/core';
import {PageResponseBorrowedBookDto} from "../../../../services/models/page-response-borrowed-book-dto";
import {BookService} from "../../../../services/services/book.service";
import {BookDto} from "../../../../services/models/book-dto";
import {FeedbackDto} from "../../../../services/models/feedback-dto";
import {BorrowedBookDto} from "../../../../services/models/borrowed-book-dto";
import {FeedbackService} from "../../../../services/services";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgxControlError} from "ngxtension/control-error";
import {BookCardComponent} from "../../components/book-card/book-card.component";
import {FaConfig, FaIconComponent, FaIconLibrary} from "@fortawesome/angular-fontawesome";
import {RouterModule} from "@angular/router";
import {fontAwesomeIcons} from "../../../../common/font-awesome-icons";
import {RatingComponent} from "../../components/rating/rating.component";

@Component({
  selector: 'app-borrowed-book-list',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, NgxControlError, RatingComponent, FaIconComponent,RouterModule],
  templateUrl: './borrowed-book-list.component.html',
  styleUrl: './borrowed-book-list.component.scss'
})
export class BorrowedBookListComponent implements OnInit{
  private faIconLibrary = inject(FaIconLibrary);
  private faConfig = inject(FaConfig);
  page = 0;
  size = 5;
  pages: any = [];
  borrowedBooks: PageResponseBorrowedBookDto = {};
  selectedBook: BookDto | undefined = undefined;
  feedbackRequest: FeedbackDto = {bookId: 0, comment: '', note: 0};
  feedbackService = inject(FeedbackService)
  bookService = inject(BookService)
  ngOnInit(): void {
    this.findAllBorrowedBooks();
    this.initFontAwesome();
  }

  private initFontAwesome() {
    this.faConfig.defaultPrefix = 'fas';
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }

  private findAllBorrowedBooks() {
    this.bookService.findAllBorrowedBooks({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (resp) => {
        this.borrowedBooks = resp;
        this.pages = Array(this.borrowedBooks.totalPages)
          .fill(0)
          .map((x, i) => i);
      }
    });
  }

  gotToPage(page: number) {
    this.page = page;
    this.findAllBorrowedBooks();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBorrowedBooks();
  }

  goToPreviousPage() {
    this.page --;
    this.findAllBorrowedBooks();
  }

  goToLastPage() {
    this.page = this.borrowedBooks.totalPages as number - 1;
    this.findAllBorrowedBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllBorrowedBooks();
  }

  get isLastPage() {
    return this.page === this.borrowedBooks.totalPages as number - 1;
  }

  returnBorrowedBook(book: BorrowedBookDto) {
    this.selectedBook = book;
    this.feedbackRequest.bookId = book.id as number;
  }

  returnBook(withFeedback: boolean) {
    this.bookService.returnBorrowBook({
      'book-id': this.selectedBook?.id as number
    }).subscribe({
      next: () => {
        if (withFeedback) {
          this.giveFeedback();
        }
        this.selectedBook = {};
        this.findAllBorrowedBooks();
      }
    });
  }

  private giveFeedback() {
    this.feedbackService.saveFeedback({
      body: this.feedbackRequest
    }).subscribe({
      next: () => {
      }
    });
  }

}
