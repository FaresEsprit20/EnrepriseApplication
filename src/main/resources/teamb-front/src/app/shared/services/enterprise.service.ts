import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EnterpriseService {

  private baseUrl = 'http://localhost:8082/api/enterprises';


  constructor(private http: HttpClient) { }


  findAllEnterprises(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    return request;
  }

  findEnterpriseById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/get/${id}`);
    return request;
  }

  saveEnterprise(enterpriseDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, enterpriseDTO);
    return request;
  }

  updateEnterprise(enterpriseDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${enterpriseDTO.id}`, enterpriseDTO);
    return request;
  }

  deleteEnterpriseById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    return request;
  }

  findDepartmentsByEnterpriseId(enterpriseId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/departments/${enterpriseId}`);
    return request;
  }

  associateDepartmentWithEnterprise(enterpriseId: number, departmentDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/departments/${enterpriseId}`, departmentDTO);
    return request;
  }

  disassociateDepartmentFromEnterprise(departmentId: number): Observable<any> {
    const request = this.http.delete<any>(`${this.baseUrl}/departments/${departmentId}`);
    return request;
  }

  findEnterpriseByDepartmentId(departmentId: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/departments/${departmentId}/enterprise`);
    return request;
  }

  
}
