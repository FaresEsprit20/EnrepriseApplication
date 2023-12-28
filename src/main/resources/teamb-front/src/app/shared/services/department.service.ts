import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  private baseUrl = 'http://localhost:8082/api/departments';

  constructor(private http: HttpClient) { }


  findAllDepartments(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    return request;
  }

  findDepartmentById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/find/${id}`);
    return request;
  }

  saveDepartment(departmentDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, departmentDTO);
    return request;
  }

  deleteDepartmentById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    return request;
  }

  updateDepartment(departmentDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentDTO.id}`, departmentDTO);
    return request;
  }

  findEmployeesByDepartmentId(departmentId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/${departmentId}/employees`);
    return request;
  }

  addEmployeeToDepartment(departmentId: number, employeeDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentId}/add-employee`, employeeDTO);
    return request;
  }

  removeEmployeeFromDepartment(departmentId: number, employeeId: number): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentId}/remove-employee/${employeeId}`, {});
    return request;
  }

  associateDepartmentWithEnterprise(enterpriseId: number, departmentId: number): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentId}/associate-enterprise/${enterpriseId}`, {});
    return request;
  }

  disassociateDepartmentFromEnterprise(departmentId: number): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentId}/disassociate-enterprise`, {});
    return request;
  }

  getEnterpriseByDepartmentId(departmentId: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/find/${departmentId}/enterprise`);
    return request;
  }

  findDepartmentsByEnterpriseId(enterpriseId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/enterprise/${enterpriseId}/departments`);
    return request;
  }

  findAll(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    return request;
  }
  

  
}
