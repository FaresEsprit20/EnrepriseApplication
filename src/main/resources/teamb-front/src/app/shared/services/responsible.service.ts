import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ResponsibleService {
  
  private baseUrl = 'http://localhost:8082/api/responsibles';
  
  constructor(private http: HttpClient) { }


  registerResponsible(request: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/register/responsible`, request);
  }


  findAllResponsibles(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/all`);
    return request;
  }

  findResponsibleById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/get/${id}`);
    return request;
  }

  findAllResponsibleByNameOrLastName(name: string, lastName: string): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/search?name=${name}&lastName=${lastName}`);
    return request;
  }

  deleteResponsibleById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    return request;
  }

  updateResponsible(responsibleDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${responsibleDTO.id}`, responsibleDTO);
    return request;
  }


  
}
