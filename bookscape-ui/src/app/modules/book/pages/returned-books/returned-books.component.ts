import {Component, inject, OnInit} from '@angular/core';
import {PageResponseBorrowedBookDto} from "../../../../services/models/page-response-borrowed-book-dto";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgxControlError} from "ngxtension/control-error";
import {RatingComponent} from "../../components/rating/rating.component";
import {FaConfig, FaIconComponent, FaIconLibrary} from "@fortawesome/angular-fontawesome";
import {RouterModule} from "@angular/router";
import {BookService} from "../../../../services/services/book.service";
import {BorrowedBookDto} from "../../../../services/models/borrowed-book-dto";
import {fontAwesomeIcons} from "../../../../common/font-awesome-icons";

@Component({
  selector: 'app-returned-books',
  standalone: true,
  imports: [CommonModule, NgxControlError, RatingComponent, FaIconComponent,RouterModule],
  templateUrl: './returned-books.component.html',
  styleUrl: './returned-books.component.scss'
})
export class ReturnedBooksComponent implements OnInit{

  page = 0;
  size = 5;
  pages: any = [];
  returnedBooks: PageResponseBorrowedBookDto = {};
  message = '';
  level: 'success' |'error' = 'success';
  bookService = inject(BookService)
  private faIconLibrary = inject(FaIconLibrary);
  private faConfig = inject(FaConfig);
  ngOnInit(): void {
    this.findAllReturnedBooks();
    this.initFontAwesome();
  }

  private findAllReturnedBooks() {
    this.bookService.findAllReturnedBooks({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (resp) => {
        this.returnedBooks = resp;
        this.pages = Array(this.returnedBooks.totalPages)
          .fill(0)
          .map((x, i) => i);
      }
    });
  }

  private initFontAwesome() {
    this.faConfig.defaultPrefix = 'fas';
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }

  gotToPage(page: number) {
    this.page = page;
    this.findAllReturnedBooks();
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllReturnedBooks();
  }

  goToPreviousPage() {
    this.page --;
    this.findAllReturnedBooks();
  }

  goToLastPage() {
    this.page = this.returnedBooks.totalPages as number - 1;
    this.findAllReturnedBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllReturnedBooks();
  }

  get isLastPage() {
    return this.page === this.returnedBooks.totalPages as number - 1;
  }

  approveBookReturn(book: BorrowedBookDto) {
    if (!book.returned) {
      return;
    }
    this.bookService.approveReturnBorrowBook({
      'book-id': book.id as number
    }).subscribe({
      next: () => {
        this.level = 'success';
        this.message = 'Book return approved';
        this.findAllReturnedBooks();
      }
    });
  }
}
