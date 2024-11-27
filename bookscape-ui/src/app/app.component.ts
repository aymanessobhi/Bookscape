import { Component, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FaConfig, FaIconComponent, FaIconLibrary } from '@fortawesome/angular-fontawesome'; // FaIconComponent is essential for icons
import { fontAwesomeIcons } from './common/font-awesome-icons';
import {NgClass} from "@angular/common";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, FaIconComponent,NgClass],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'bookscape-ui';
  private faIconLibrary = inject(FaIconLibrary);
  private faConfig = inject(FaConfig);

  ngOnInit(): void {
    this.initFontAwesome();
  }

  private initFontAwesome() {
    this.faConfig.defaultPrefix = 'fas';
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }
}
