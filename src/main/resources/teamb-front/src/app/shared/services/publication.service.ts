import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PublicationService  {
  
  private baseUrl = 'http://localhost:8082/api/publications';

  constructor(private http: HttpClient) {}

  findAllPublications(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    return request;
  }

  findPublicationById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/get/${id}`);  
    return request;
  }

  findAllByEmployeeId(employeeId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/employee/${employeeId}`);
    return request;
  }

  createPublication(publicationDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, publicationDTO);
    return request;
  }

  updatePublication(publicationId: number, publicationDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${publicationId}`, publicationDTO);    
    return request;
  }

  deletePublication(publicationId: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${publicationId}`);
    return request;
  }

  findEmployeeByPublicationId(publicationId: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/${publicationId}/employee`);
    return request;
  }

  associateEmployeeWithPublication(publicationId: number, employeeId: number): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/${publicationId}/employee/${employeeId}`, {});
    return request;
  }

  disassociateEmployeeFromPublication(publicationId: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/${publicationId}/employee`);
    return request;
  }

  getRatingForPublication(publicationId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/${publicationId}/rating`);
    return request;
  }

  
}
