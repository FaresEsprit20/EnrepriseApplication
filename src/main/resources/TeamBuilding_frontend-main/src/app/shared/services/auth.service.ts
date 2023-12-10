import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError, of  } from 'rxjs';
import { catchError, switchMap, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8082/api/v1/auth';
  private accessToken: string | null = null;

  constructor(private http: HttpClient) { }

  registerEmployee(request: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register/employee`, request);
  }

  registerResponsible(request: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register/responsible`, request);
  }

  authenticate(request: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/authenticate`, request)
      .pipe(
        tap(response => this.handleAuthenticationResponse(response)),
        catchError(error => this.handleAuthenticationError(error))
      );
  }

  private handleAuthenticationResponse(response: any): void {
    console.log('Authentication response:', response);

    if (response && response.access_token) {
      this.setAccessToken(response.access_token);
      console.log('AuthService/handleAuthenticationResponse    Access token set: Okay');
    } else {
      console.error('AuthService/handleAuthenticationResponse  Invalid authentication response:', response);
    }
  }

  private handleAuthenticationError(error: any): Observable<any> {
    console.error('AuthService/handleAuthenticationError  Authentication error:', error);
    return throwError('AuthService/handleAuthenticationError  Authentication failed');
  }

  //attach method to request with cookies or bearer token
  public attachTokenToRequest(request: Observable<any>): Observable<any> {
    return request.pipe(
      switchMap(req => {
        console.warn("Attach Token   Access Token  :  ")
        // Check if Authorization header is already present
        if (req.headers.has('Authorization') && this.accessToken) {
          console.warn("Attach Token   Authorization and access token are present")
          // If Authorization header is present and accessToken is available, include it
          req = req.clone({
            setHeaders: {
              Authorization: `Bearer ${this.accessToken}`,
            },
          });
        } else {
          // If Authorization header is not present or accessToken is null, include credentials (cookies)
          console.warn("Attach Token   Htp only cookies are present")
          req = req.clone({
            withCredentials: true,
          });
        }
        return req;
      })
    );
  }


  setAccessToken(token: string) {
    this.accessToken = token;
  }

  getAccessToken() {
    return this.accessToken;
  }

  private getHeaders(): HttpHeaders {
    return new HttpHeaders();
  }

  //extract non http only cookie, not effecient with cookie that are http only
 /*  private extractTokenFromCookies(): string | null {
    const accessTokenCookie = document.cookie
      .split('; ')
      .find(row => row.startsWith('accessToken='));
    if (accessTokenCookie) {
      const token = accessTokenCookie.split('=')[1];
      return token;
    }
    return null;
  }
 */

}
