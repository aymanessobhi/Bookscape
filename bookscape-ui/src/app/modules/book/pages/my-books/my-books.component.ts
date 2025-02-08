import {Component, inject, OnInit} from '@angular/core';
import {PageResponseBookDto} from "../../../../services/models/page-response-book-dto";
import {FaConfig, FaIconComponent, FaIconLibrary} from "@fortawesome/angular-fontawesome";
import {BookService} from "../../../../services/services/book.service";
import {Router, RouterModule} from "@angular/router";
import {fontAwesomeIcons} from "../../../../common/font-awesome-icons";
import {BookDto} from "../../../../services/models/book-dto";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgxControlError} from "ngxtension/control-error";
import {BookCardComponent} from "../../components/book-card/book-card.component";

@Component({
  selector: 'app-my-books',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, NgxControlError, BookCardComponent, FaIconComponent,RouterModule],
  templateUrl: './my-books.component.html',
  styleUrl: './my-books.component.scss'
})
export class MyBooksComponent implements OnInit{
  bookResponse: PageResponseBookDto = {};
  private faIconLibrary = inject(FaIconLibrary);
  private faConfig = inject(FaConfig);
  bookService = inject(BookService)
  pages: any = [];
  router = inject(Router)
  page = 0;
  size = 4;
  message = '';
  level: 'success' |'error' = 'success';
  ngOnInit() {
    this.findAllBooks();
    this.initFontAwesome();
  }

  private initFontAwesome() {
    this.faConfig.defaultPrefix = 'fas';
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }

  private findAllBooks() {
    this.bookService.findAllBooksByOwner(
      {
        page: this.page,
        size: this.size
      }).subscribe({
      next:(books)=>{
        this.bookResponse = books;
        this.pages = Array(this.bookResponse.totalPages)
          .fill(0)
          .map((x, i) => i);
      }
    })
  }

  goToFirstPage() {
    this.page = 0;
    this.findAllBooks();
  }

  goToPreviousPage() {
    this.page --;
    this.findAllBooks();
  }

  gotToPage(page: number) {
    this.page = page;
    this.findAllBooks();
  }

  goToNextPage() {
    this.page++;
    this.findAllBooks();
  }

  goToLastPage() {
    this.page = this.bookResponse.totalPages as number - 1;
    this.findAllBooks();
  }

  get isLastPage() {
    return this.page === this.bookResponse.totalPages as number - 1;
  }

  archiveBook(book: BookDto) {
    this.bookService.updateArchivedStatus({
      'book-id': book.id as number
    }).subscribe({
      next: () => {
        book.archived = !book.archived;
      }
    });
  }

  shareBook(book: BookDto) {
    this.bookService.updateShareableStatus({
      'book-id': book.id as number
    }).subscribe({
      next: () => {
        book.shareable = !book.shareable;
      }
    });
  }

  editBook(book: BookDto) {
    this.router.navigate(['books', 'manage', book.id]);
  }
}
