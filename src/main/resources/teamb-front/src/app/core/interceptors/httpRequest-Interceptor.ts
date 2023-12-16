import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of, switchMap } from "rxjs";
import { AuthService } from "../../shared/services/auth.service";


@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

  
  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Call attachTokenToRequest with the current request

    if (this.shouldExclude(request.url)) {
      // If excluded, continue with the original request
      return next.handle(request);
    }

    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${this.authService.getAccessToken()}`,
      },
    });
    console.log("intercepted")
        // If attachTokenToRequest returns null, continue with the original request
        return next.handle(request);
   
  }

  private shouldExclude(url: string): boolean {
    // Add logic to check if the URL should be excluded from token attachment
    return url.includes("http://localhost:8082/api/v1/auth");
  }


}
