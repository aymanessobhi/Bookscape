import {Component, inject, OnInit} from '@angular/core';
import {BookService} from "../../../../services/services/book.service";
import {Router} from "@angular/router";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgxControlError} from "ngxtension/control-error";
import {PageResponseBookDto} from "../../../../services/models/page-response-book-dto";

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, NgxControlError],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.scss'
})
export class BookListComponent implements OnInit{
  bookResponse: PageResponseBookDto = {};
  bookService = inject(BookService)
  router = inject(Router)
  private page = 0;
  private size = 5;

  ngOnInit() {
    this.findAllBooks();
  }

  private findAllBooks() {
    this.bookService.findAllBooks(
      {
          page: this.page,
          size: this.size
      }).subscribe({
          next:(books)=>{
            this.bookResponse = books;
          }
    })
  }
}
