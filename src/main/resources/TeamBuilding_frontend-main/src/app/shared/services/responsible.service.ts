import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class ResponsibleService {

  private baseUrl = 'http://localhost:8082/api/responsibles';

  constructor(private http: HttpClient, private authService: AuthService) { }

  findAllResponsibles(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/all`);
    return this.authService.attachTokenToRequest(request);
  }

  findResponsibleById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/get/${id}`);
    return this.authService.attachTokenToRequest(request);
  }

  findAllResponsibleByNameOrLastName(name: string, lastName: string): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/search?name=${name}&lastName=${lastName}`);
    return this.authService.attachTokenToRequest(request);
  }

  saveResponsible(responsibleDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, responsibleDTO);
    return this.authService.attachTokenToRequest(request);
  }

  deleteResponsibleById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    return this.authService.attachTokenToRequest(request);
  }

  updateResponsible(responsibleDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${responsibleDTO.id}`, responsibleDTO);
    return this.authService.attachTokenToRequest(request);
  }

  
}
