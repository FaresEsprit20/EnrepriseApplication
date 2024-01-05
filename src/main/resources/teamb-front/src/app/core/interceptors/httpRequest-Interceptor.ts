import {  HttpErrorResponse, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { catchError, throwError } from "rxjs";
import { AuthService } from "../../shared/services/auth.service";


export const HttpRequestInterceptor: HttpInterceptorFn = (
  request: HttpRequest<unknown>,
  next: HttpHandlerFn
) => {
  if (shouldExclude(request.url)) {
    // If excluded, continue with the original request
    console.log("skip interceptor");
    return next(request);
  }
  const authService = inject(AuthService);
  const accessToken = authService.getAccessToken()

  
  
   // Adjust this instantiation as needed
   const headers = { Authorization: `Bearer ${accessToken}` }
   request = request.clone({
    setHeaders: {
      Authorization: `Bearer ${authService.getAccessToken()}`,
    },
  });
  console.log("intercepted");

  // If attachTokenToRequest returns null, continue with the original request
  return next(request).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMsg = "";
      if (error.error instanceof ErrorEvent) {
        console.log("this is client side error");
        errorMsg = `Client Error: ${error.error.message}`;
      } else {
        console.log("this is server side error");
        errorMsg = `Server Error Code: ${error.status}, Message: ${error.message}`;
      }

      console.log(errorMsg);
      return throwError(() => errorMsg);
    })
  ); 
};

function shouldExclude(url: string): boolean {
  // Add logic to check if the URL should be excluded from token attachment
  return url.includes("http://localhost:8082/api/v1/auth");
}
