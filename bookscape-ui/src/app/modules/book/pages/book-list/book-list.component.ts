import {Component, inject, OnInit} from '@angular/core';
import {BookService} from "../../../../services/services/book.service";
import {Router} from "@angular/router";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgxControlError} from "ngxtension/control-error";
import {PageResponseBookDto} from "../../../../services/models/page-response-book-dto";
import {BookCardComponent} from "../../components/book-card/book-card.component";
import {fontAwesomeIcons} from "../../../../common/font-awesome-icons";
import {FaConfig, FaIconComponent, FaIconLibrary} from "@fortawesome/angular-fontawesome";

@Component({
  selector: 'app-book-list',
  standalone: true,
    imports: [CommonModule, FormsModule, ReactiveFormsModule, NgxControlError, BookCardComponent, FaIconComponent],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.scss'
})
export class BookListComponent implements OnInit{
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
    this.bookService.findAllBooks(
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
}
