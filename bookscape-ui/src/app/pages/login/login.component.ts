import {Component, inject, NgModule} from '@angular/core';
import {
  FormBuilder,
  FormGroup, FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { NgxControlError } from 'ngxtension/control-error';
import { AuthService } from '../../services/services/auth.service';
import { AuthRequest } from '../../services/models/auth-request';
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, NgxControlError],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'], // Fixed typo
})
export class LoginComponent {
  formBuilder = inject(FormBuilder);
  router = inject(Router);
  authService = inject(AuthService);
  tokenService = inject(TokenService);
  errorMsg: Array<string> = [];

  authRequest: AuthRequest = {email: '', password: ''};

  login(): void {
    this.errorMsg = [];
    this.authService.authenticate({
        body: this.authRequest
      }).subscribe(
      {
        next:(res) =>{
          this.tokenService.token = res.token as string;
          this.router.navigate(['books'])
        },
        error:(err) =>{
          console.log(err);
          if(err.error.validationError){
            this.errorMsg = err.error.validationError;
          }else{
            this.errorMsg.push(err.error.error)
          }
        }
      })
  }


  register(): void {
    this.router.navigate(['register']);
  }

}
