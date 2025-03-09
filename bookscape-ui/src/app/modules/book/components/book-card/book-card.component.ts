import {Component, EventEmitter, inject, Input, OnInit, Output} from '@angular/core';
import {CommonModule} from "@angular/common";
import {BookDto} from "../../../../services/models/book-dto";
import {FaConfig, FaIconComponent, FaIconLibrary} from "@fortawesome/angular-fontawesome";
import {fontAwesomeIcons} from "../../../../common/font-awesome-icons";
import {RatingComponent} from "../rating/rating.component";


@Component({
  selector: 'app-book-card',
  standalone: true,
  imports: [CommonModule, FaIconComponent, RatingComponent],
  templateUrl: './book-card.component.html',
  styleUrl: './book-card.component.scss'
})
export class BookCardComponent implements OnInit{
  private _book: BookDto = {}
  private _bookCover: string | undefined;
  private faIconLibrary = inject(FaIconLibrary);
  private faConfig = inject(FaConfig);
  private _manage = false;

  ngOnInit(): void {
    this.initFontAwesome();
  }

  private initFontAwesome() {
    this.faConfig.defaultPrefix = 'fas';
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }

  get bookCover(): string | undefined {
    if (this._book.cover != null) {
      return 'data:image/jpg;base64,' + this._book.cover
    }
    return 'https://picsum.photos/1900/800';
  }

  get book(): BookDto {
    return this._book;
  }

  @Input()
  set book(value: BookDto) {
    this._book = value;
  }

  get manage(): boolean {
    return this._manage;
  }

  @Input()
  set manage(value: boolean) {
    this._manage = value;
  }

  @Output() private share: EventEmitter<BookDto> = new EventEmitter<BookDto>();
  @Output() private archive: EventEmitter<BookDto> = new EventEmitter<BookDto>();
  @Output() private addToWaitingList: EventEmitter<BookDto> = new EventEmitter<BookDto>();
  @Output() private borrow: EventEmitter<BookDto> = new EventEmitter<BookDto>();
  @Output() private edit: EventEmitter<BookDto> = new EventEmitter<BookDto>();
  @Output() private details: EventEmitter<BookDto> = new EventEmitter<BookDto>();

  onShare() {
    this.share.emit(this._book);
  }

  onArchive() {
    this.archive.emit(this._book);
  }

  onAddToWaitingList() {
    this.addToWaitingList.emit(this._book);
  }

  onBorrow() {
    this.borrow.emit(this._book);
  }

  onEdit() {
    this.edit.emit(this._book);
  }

  onShowDetails() {
    this.details.emit(this._book);
  }
}
