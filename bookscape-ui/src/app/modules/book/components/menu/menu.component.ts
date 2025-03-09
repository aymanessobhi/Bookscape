import {Component, OnInit,inject} from '@angular/core';
import {CommonModule, DOCUMENT} from "@angular/common";
import {routes} from "../../../../app.routes";
import {Router, RouterModule} from "@angular/router";
import {FaConfig, FaIconComponent, FaIconLibrary} from "@fortawesome/angular-fontawesome";
import {fontAwesomeIcons} from "../../../../common/font-awesome-icons";


@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule,RouterModule,FaIconComponent],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit{
  private faIconLibrary = inject(FaIconLibrary);
  private faConfig = inject(FaConfig);
  router = inject(Router)

  logout() {
    localStorage.removeItem('token');
    window.location.reload();
  }
  ngOnInit(): void {
    this.initFontAwesome();
  }

  private initFontAwesome() {
    this.faConfig.defaultPrefix = 'fas';
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }
}
