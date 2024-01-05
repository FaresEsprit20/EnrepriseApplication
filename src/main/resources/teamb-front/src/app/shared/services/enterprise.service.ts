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



  addEmployeeToEnterprise(enterpriseDTO: any): Observable<any> {
    const url = `${this.baseUrl}/api/enterprises/add-employee`;
    return this.http.put<any>(url, enterpriseDTO);
  }

  deleteEmployeeFromEnterprise(enterpriseDTO: any): Observable<any> {
    const url = `${this.baseUrl}/api/enterprises/delete-employee`;
    return this.http.put<any>(url, enterpriseDTO);
  }

  addResponsibleToEnterprise(enterpriseDTO: any): Observable<any> {
    const url = `${this.baseUrl}/api/enterprises/add-responsible`;
    return this.http.put<any>(url, enterpriseDTO);
  }

  deleteResponsibleFromEnterprise(enterpriseDTO: any): Observable<any> {
    const url = `${this.baseUrl}/api/enterprises/delete-responsible`;
    return this.http.put<any>(url, enterpriseDTO);
  }
  
  getAllEmployees(enterpriseId: any): Observable<any[]> {
    const url = `${this.baseUrl}/api/enterprises/employees/${enterpriseId}/find/all`;
    return this.http.get<any[]>(url);
  }

  getAllResponsibles(enterpriseId: any): Observable<any[]> {
    const url = `${this.baseUrl}/api/enterprises/responsibles/${enterpriseId}/find/all`;
    return this.http.get<any[]>(url);
  }

}
