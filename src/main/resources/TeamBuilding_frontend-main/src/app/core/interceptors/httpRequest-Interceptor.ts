import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of, switchMap } from "rxjs";
import { AuthService } from "src/app/shared/services/auth.service";

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Call attachTokenToRequest with the current request
     request = request.clone({
      withCredentials: true,
    });
        // If attachTokenToRequest returns null, continue with the original request
        return next.handle(request);
   
  }
}
