import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EnterpriseService {

  private baseUrl = 'http://localhost:8082/api/enterprises';


  constructor(private http: HttpClient) { }


  getAllEnterprises(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/find/all`);
  }

  getEnterpriseById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/get/${id}`);
  }

  createEnterprise(enterpriseDTO: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/create`, enterpriseDTO);
  }

  updateEnterprise(id: number, enterpriseDTO: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/update/${id}`, enterpriseDTO);
  }

  deleteEnterprise(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
  }

  getDepartmentsByEnterpriseId(enterpriseId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/departments/${enterpriseId}`);
  }

  associateDepartmentForEnterprise(
    enterpriseId: number,
    departmentDTO: any
  ): Observable<any> {
    return this.http.post<any>(
      `${this.baseUrl}/departments/${enterpriseId}`,
      departmentDTO
    );
  }

  disassociateDepartmentFromEnterprise(
    departmentId: number
  ): Observable<any> {
    return this.http.delete<any>(
      `${this.baseUrl}/departments/${departmentId}`
    );
  }

  getEnterpriseByDepartmentId(departmentId: number): Observable<any> {
    return this.http.get<any>(
      `${this.baseUrl}/departments/${departmentId}/enterprise`
    );
  }

  
}
