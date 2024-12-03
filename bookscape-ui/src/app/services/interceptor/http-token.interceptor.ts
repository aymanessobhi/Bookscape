import {HttpHeaders, HttpInterceptorFn} from '@angular/common/http';
import {TokenService} from "../token/token.service";
import {inject} from "@angular/core";


export const httpTokenInterceptor: HttpInterceptorFn = (req, next) => {
  let tokenService = inject(TokenService);

  const token: string | null = tokenService.token;
  if(token){
    const authReq =req.clone({
      headers : new HttpHeaders({
        Authorization: `Bearer ${token}`
      })
    });
    return next(authReq);
  }
  return next(req);
};
