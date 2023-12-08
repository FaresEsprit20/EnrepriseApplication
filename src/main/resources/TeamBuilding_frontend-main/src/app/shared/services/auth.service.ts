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
      console.log('Access token set:', this.accessToken);
    } else {
      console.error('Invalid authentication response:', response);
    }
  }

  private handleAuthenticationError(error: any): Observable<any> {
    console.error('Authentication error:', error);
    return throwError('Authentication failed');
  }

  public attachTokenToRequest(request: Observable<any>): Observable<any> {
    if (!this.accessToken) {
      console.log('Access token is null. Redirecting to login...');
      return throwError('Access token is null');
    }

    return request.pipe(
      switchMap(req => {
        if (req.headers.has('Authorization')) {
          return req;
        }

        req = req.clone({
          setHeaders: {
            Authorization: `Bearer ${this.accessToken}`,
          },
        });
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
    return new HttpHeaders({
      'accessToken': `${this.accessToken}`
    });
  }

  private extractTokenFromCookies(): string | null {
    const accessTokenCookie = document.cookie
      .split('; ')
      .find(row => row.startsWith('accessToken='));
    if (accessTokenCookie) {
      const token = accessTokenCookie.split('=')[1];
      return token;
    }
    return null;
  }


}
