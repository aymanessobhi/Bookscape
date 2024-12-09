import {Component, OnInit,inject} from '@angular/core';
import {CommonModule, DOCUMENT} from "@angular/common";
import {routes} from "../../../../app.routes";
import {Router} from "@angular/router";


@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit{

  router = inject(Router)
  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['login']);

  }

  ngOnInit(): void {

  }

}
