import {Component, Input} from '@angular/core';
import {CommonModule} from "@angular/common";
import {BookDto} from "../../../../services/models/book-dto";


@Component({
  selector: 'app-book-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './book-card.component.html',
  styleUrl: './book-card.component.scss'
})
export class BookCardComponent {
  private _book: BookDto = {}
  private _bookCover: string | undefined;

  get bookCover(): string | undefined {
    if (this._book.cover != null) {
      return 'data:image/jpg;base64,' + this._book.cover
    }
    return 'https://source.unsplash.com/user/c_v_r/1900x800';
  }

  get book(): BookDto {
    return this._book;
  }

  @Input()
  set book(value: BookDto) {
    this._book = value;
  }


}
