import {Component, inject, OnInit} from '@angular/core';
import {BookDto} from "../../../../services/models/book-dto";
import {BookService} from "../../../../services/services/book.service";
import {ActivatedRoute, Router, RouterModule} from "@angular/router";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgxControlError} from "ngxtension/control-error";
import {BookCardComponent} from "../../components/book-card/book-card.component";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";

@Component({
  selector: 'app-manage',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, NgxControlError, BookCardComponent, FaIconComponent,RouterModule],
  templateUrl: './manage.component.html',
  styleUrl: './manage.component.scss'
})
export class ManageComponent  implements OnInit{
  errorMsg: Array<string> = [];
  bookRequest: BookDto= {
    authorName: '',
    isbn: '',
    synopsis: '',
    title: ''
  };
  selectedBookCover: any;
  selectedPicture: string | undefined;
  bookService = inject(BookService)
  router = inject(Router)
  activatedRoute = inject(ActivatedRoute)

  ngOnInit() {
    const bookId = this.activatedRoute.snapshot.params['bookId'];
    if (bookId) {
      this.bookService.findBookById({
        'book-id': bookId
      }).subscribe({
        next: (book) => {
          this.bookRequest = {
            id: book.id,
            title: book.title as string,
            authorName: book.authorName as string,
            isbn: book.isbn as string,
            synopsis: book.synopsis as string,
            shareable: book.shareable
          }
          if(book.cover){
            this.selectedPicture = 'data:image/jpg;base64,' +book.cover;
          }
        }
      });
    }
  }

  saveBook() {
    this.bookService.saveBook({
      body: this.bookRequest
    }).subscribe({
      next: (book) => {
        this.bookService.uploadBookCoverPicture({
          'book-id': book.id as number,
          body: {
            file: this.selectedBookCover
          }
        }).subscribe({
          next: () => {
            this.router.navigate(['/books/my-books']);
          }
        });
      },
      error: (err) => {
        console.log(err.error);
        this.errorMsg = err.error.validationErrors;
      }
    });
  }

  onFileSelected(event: any) {
    this.selectedBookCover = event.target.files[0];
    console.log(this.selectedBookCover);

    if (this.selectedBookCover) {

      const reader = new FileReader();
      reader.onload = () => {
        this.selectedPicture = reader.result as string;
      };
      reader.readAsDataURL(this.selectedBookCover);
    }
  }

  logCheckboxValue() {
    console.log('Checkbox Value:', this.bookRequest.shareable);
  }
}
