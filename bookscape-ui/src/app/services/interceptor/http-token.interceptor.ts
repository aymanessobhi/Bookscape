import {HttpHeaders, HttpInterceptorFn} from '@angular/common/http';
import {TokenService} from "../token/token.service";
import {inject} from "@angular/core";


export const httpTokenInterceptor: HttpInterceptorFn = (req, next) => {
  let tokenService = inject(TokenService);

  const token  = tokenService.token;

  console.log('Token:', token);
  console.log(req.headers.get('Authorization'));

  if(token){
    const authReq =req.clone({
      setHeaders:{
        Authorization: `Bearer ${token}`
      }
    });
    return next(authReq);
  }
  return next(req);
};
