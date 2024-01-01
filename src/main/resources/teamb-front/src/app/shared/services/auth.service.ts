import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { catchError, map, switchMap, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8082/api/v1/auth';
  private accessToken: string | null = null;
  private refreshToken: string | null = null;

  constructor(private http: HttpClient) { }


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

  // Attach method to request with session storage token
  public attachTokenToRequest(request: Observable<any>): Observable<any> {
    return request
  }
  
  

  setAccessToken(token: string) {
    // Store the token in session storage
    sessionStorage.setItem('accessToken', token);
    this.accessToken = token;
  }

  getAccessToken() {
    this.accessToken = sessionStorage.getItem('accessToken');
    return sessionStorage.getItem('accessToken');
  }

  setRefreshToken(token: string) {
    // Store the refresh token in session storage
    sessionStorage.setItem('refreshToken', token);
    this.refreshToken = token;
  }

  getRefreshToken() {
    // Retrieve the refresh token from session storage
    return this.refreshToken || sessionStorage.getItem('refreshToken');
  }

  private getHeaders(): HttpHeaders {
    return new HttpHeaders();
  }

  clearAccessToken(): void {
    sessionStorage.removeItem("accessToken");
    sessionStorage.removeItem("refreshToken");
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
