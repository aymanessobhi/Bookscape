import {Component, inject} from '@angular/core';
import {RegistrationRequest} from "../../services/models/registration-request";
import {Router} from "@angular/router";
import {AuthService} from "../../services/services/auth.service";
import {TokenService} from "../../services/token/token.service";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgxControlError} from "ngxtension/control-error";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, NgxControlError],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  router = inject(Router);
  authService = inject(AuthService);
  tokenService = inject(TokenService);
  errorMsg: Array<string> = [];
  registerRequest: RegistrationRequest = { email: '', firstname:'', lastname:'',password:''}

  register(){
    this.errorMsg = [];
    this.authService.register({
      body: this.registerRequest
    })
      .subscribe({
        next: () => {
          this.router.navigate(['activate-account']);
        },
        error: (err) => {
          this.errorMsg = err.error.validationError;
        }
      });
  }

  login(){
    this.router.navigate(['login']);
  }
}
