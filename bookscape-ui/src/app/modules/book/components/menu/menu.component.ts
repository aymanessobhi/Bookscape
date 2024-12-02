import {Component, OnInit,inject} from '@angular/core';
import {CommonModule, DOCUMENT} from "@angular/common";


@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit{

  document = inject(DOCUMENT)
  logout() {
    localStorage.removeItem('token');
    window.location.reload();
  }

  ngOnInit(): void {

  }

}
