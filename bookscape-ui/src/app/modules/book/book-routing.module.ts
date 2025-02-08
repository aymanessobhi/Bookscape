import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MainComponent} from "./pages/main/main.component";
import {BookListComponent} from "./pages/book-list/book-list.component";
import {MyBooksComponent} from "./pages/my-books/my-books.component";
import {ManageComponent} from "./pages/manage/manage.component";

const routes: Routes = [
  {
    path : '',
    component: MainComponent,
    children:[
      {
        path: '',
        component:  BookListComponent
      },
      {
        path: 'my-books',
        component:  MyBooksComponent
      },
      {
        path: 'manage',
        component:  ManageComponent
      },
      {
        path: 'manage/:bookId',
        component:  ManageComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookRoutingModule { }
