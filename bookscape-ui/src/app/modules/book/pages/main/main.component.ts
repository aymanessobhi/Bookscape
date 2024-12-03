import { Component } from '@angular/core';
import {MenuComponent} from "../../components/menu/menu.component";
import {RouterOutlet} from "@angular/router";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [
    CommonModule,
    MenuComponent,
    RouterOutlet
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss'
})
export class MainComponent {

}
