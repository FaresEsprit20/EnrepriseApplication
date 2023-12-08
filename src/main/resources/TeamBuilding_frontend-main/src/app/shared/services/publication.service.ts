import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, filter, of, switchMap, take, tap } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class PublicationService {

  private baseUrl = 'http://localhost:8082/api/publications';

  constructor(private http: HttpClient, private authService: AuthService) {}

  findAllPublications() : Observable<any> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    return this.authService.attachTokenToRequest(request);
  }
  
  
  findPublicationById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/get/${id}`);
    return this.authService.attachTokenToRequest(request);
  }

  findAllByEmployeeId(employeeId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/employee/${employeeId}`);
    return this.authService.attachTokenToRequest(request);
  }

  createPublication(publicationDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, publicationDTO);
    return this.authService.attachTokenToRequest(request);
  }

  updatePublication(publicationId: number, publicationDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${publicationId}`, publicationDTO);
    return this.authService.attachTokenToRequest(request);
  }

  deletePublication(publicationId: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${publicationId}`);
    return this.authService.attachTokenToRequest(request);
  }

  findEmployeeByPublicationId(publicationId: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/${publicationId}/employee`);
    return this.authService.attachTokenToRequest(request);
  }

  associateEmployeeWithPublication(publicationId: number, employeeId: number): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/${publicationId}/employee/${employeeId}`, {});
    return this.authService.attachTokenToRequest(request);
  }

  disassociateEmployeeFromPublication(publicationId: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/${publicationId}/employee`);
    return this.authService.attachTokenToRequest(request);
  }

  getRatingForPublication(publicationId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/${publicationId}/rating`);
    return this.authService.attachTokenToRequest(request);
  }

  
}
