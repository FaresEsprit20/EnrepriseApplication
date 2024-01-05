import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable,  throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8082/api/v1/auth';
  private accessToken: string | null = null;
  private refreshToken: string | null = null;
  private role: string | null = null;
  private userId: string | null = null;
  private email: string | null = null;
  private authenticated: string | null = null;

  constructor(private http: HttpClient, private cookieService: CookieService) { }

  authenticate(request: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/authenticate`, request)
      .pipe(
        tap(response => this.handleAuthenticationResponse(response)),
        catchError(error => this.handleAuthenticationError(error))
      );
  }

  refreshTokenMethod(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/refresh-token`)
      .pipe(
        tap(response => this.handleRefreshTokenResponse(response)),
        catchError(error => this.handleAuthenticationError(error))
      );
  }

  isTokenExpired(): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/is-token-expired`, {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${this.accessToken}`
      })
    });
  }

  private handleAuthenticationResponse(response: any): void {
    console.log('Authentication response:');

    if (response && response.access_token && response.refresh_token) {
      this.setAccessToken(response.access_token);
      this.setRefreshToken(response.refresh_token);
      this.setRole(response.role)
      this.setEmail(response.email)
      this.setUserId(response.userId)
      this.setAuthenticated('true');
      console.log('AuthService/handleAuthenticationResponse    Access token set: Okay');
    } else {
      console.error('AuthService/handleAuthenticationResponse  Invalid authentication response:', response);
    }
  }

  private handleRefreshTokenResponse(response: any): void {
    console.log('Refresh token response:', response);

    if (response && response.access_token && response.refresh_token) {
      this.setAccessToken(response.access_token);
      this.setRefreshToken(response.refresh_token);
      console.log('AuthService/handleRefreshTokenResponse    Access token refreshed: Okay');
    } else {
      console.error('AuthService/handleRefreshTokenResponse  Invalid refresh token response:', response);
    }
  }

  private handleAuthenticationError(error: any): Observable<any> {
    console.error('AuthService/handleAuthenticationError  Authentication error:', error);
    return throwError('AuthService/handleAuthenticationError  Authentication failed');
  }

  setAuthenticated(auth: string): void {
    this.cookieService.set('connected', 'true', 12 * 60 * 60); // 12 hours in seconds
    this.authenticated = auth;
  }

  getAuthenticated(): string | null {
    this.authenticated = this.cookieService.get('connected');
    return this.cookieService.get('connected');
  }

  setRole(role: string): void {
    this.cookieService.set('role', role, 12 * 60 * 60); // 12 hours in seconds
    this.role = role;
  }

  getRole(): string | null {
    this.role = this.cookieService.get('role');
    return this.cookieService.get('role');
  }

  setUserId(userId: string): void {
    this.cookieService.set('userId', userId, 12 * 60 * 60); // 12 hours in seconds
    this.userId = userId;
  }

  getUserId(): string | null {
    this.userId = this.cookieService.get('userId');
    return this.cookieService.get('userId');
  }

  setEmail(email: string): void {
    this.cookieService.set('email', email, 12 * 60 * 60); // 12 hours in seconds
    this.email = email;
  }

  getEmail(): string | null {
    this.email = this.cookieService.get('email');
    return this.cookieService.get('email');
  }

  setAccessToken(token: string): void {
    this.cookieService.set('accessToken', token, 12 * 60 * 60); // 12 hours in seconds
    this.accessToken = token;
  }

  getAccessToken(): string | null {
    this.accessToken = this.cookieService.get('accessToken');
    return this.cookieService.get('accessToken');
  }

  setRefreshToken(token: string): void {
    this.cookieService.set('refreshToken', token, 12 * 60 * 60); // 12 hours in seconds
    this.refreshToken = token;
  }

  getRefreshToken(): string | null {
    this.refreshToken = this.cookieService.get('refreshToken');
    return this.cookieService.get('refreshToken');
  }

  private getHeaders(): HttpHeaders {
    return new HttpHeaders();
  }

  clearAccessToken(): void {
    this.cookieService.delete('accessToken');
    this.cookieService.delete('refreshToken');
    this.cookieService.delete('role');
    this.cookieService.delete('userId');
    this.cookieService.delete('email');
    this.cookieService.delete('connected');
  }

  logout(): Observable<any> {
    // Make a request to the logout endpoint on the server
    return this.http.post('http://localhost:8082/api/v1/auth/logout', {}).pipe(
      // Handle successful logout on the client side
      map(() => {
        // Clear the access token and any other user-related data
        this.clearAccessToken();
      })
    );
  }


  
}
