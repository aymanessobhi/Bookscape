import {HttpHeaders, HttpInterceptorFn} from '@angular/common/http';
import {TokenService} from "../token/token.service";
import { inject, PLATFORM_ID } from '@angular/core';
import {isPlatformBrowser} from "@angular/common";


export const httpTokenInterceptor: HttpInterceptorFn = (req, next) => {
  let tokenService = inject(TokenService);

  const platformId = inject(PLATFORM_ID);

  const token = isPlatformBrowser(platformId) ? tokenService.token : null;

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
