import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class EnterpriseService {

  private baseUrl = 'http://localhost:8082/api/enterprises';

  constructor(private http: HttpClient, private authService: AuthService) { }

  findAllEnterprises(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    return this.authService.attachTokenToRequest(request);
  }

  findEnterpriseById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/get/${id}`);
    return this.authService.attachTokenToRequest(request);
  }

  saveEnterprise(enterpriseDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, enterpriseDTO);
    return this.authService.attachTokenToRequest(request);
  }

  updateEnterprise(enterpriseDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${enterpriseDTO.id}`, enterpriseDTO);
    return this.authService.attachTokenToRequest(request);
  }

  deleteEnterpriseById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    return this.authService.attachTokenToRequest(request);
  }

  findDepartmentsByEnterpriseId(enterpriseId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/departments/${enterpriseId}`);
    return this.authService.attachTokenToRequest(request);
  }

  associateDepartmentWithEnterprise(enterpriseId: number, departmentDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/departments/${enterpriseId}`, departmentDTO);
    return this.authService.attachTokenToRequest(request);
  }

  disassociateDepartmentFromEnterprise(departmentId: number): Observable<any> {
    const request = this.http.delete<any>(`${this.baseUrl}/departments/${departmentId}`);
    return this.authService.attachTokenToRequest(request);
  }

  findEnterpriseByDepartmentId(departmentId: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/departments/${departmentId}/enterprise`);
    return this.authService.attachTokenToRequest(request);
  }

  
}
