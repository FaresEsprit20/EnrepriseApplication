import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  private baseUrl = 'http://localhost:8082/api/departments';

  constructor(private http: HttpClient, private authService: AuthService) { }

  findAllDepartments(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    return this.authService.attachTokenToRequest(request);
  }

  findDepartmentById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/find/${id}`);
    return this.authService.attachTokenToRequest(request);
  }

  saveDepartment(departmentDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, departmentDTO);
    return this.authService.attachTokenToRequest(request);
  }

  deleteDepartmentById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    return this.authService.attachTokenToRequest(request);
  }

  updateDepartment(departmentDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentDTO.id}`, departmentDTO);
    return this.authService.attachTokenToRequest(request);
  }

  findEmployeesByDepartmentId(departmentId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/${departmentId}/employees`);
    return this.authService.attachTokenToRequest(request);
  }

  addEmployeeToDepartment(departmentId: number, employeeDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentId}/add-employee`, employeeDTO);
    return this.authService.attachTokenToRequest(request);
  }

  removeEmployeeFromDepartment(departmentId: number, employeeId: number): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentId}/remove-employee/${employeeId}`, {});
    return this.authService.attachTokenToRequest(request);
  }

  associateDepartmentWithEnterprise(enterpriseId: number, departmentId: number): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentId}/associate-enterprise/${enterpriseId}`, {});
    return this.authService.attachTokenToRequest(request);
  }

  disassociateDepartmentFromEnterprise(departmentId: number): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentId}/disassociate-enterprise`, {});
    return this.authService.attachTokenToRequest(request);
  }

  getEnterpriseByDepartmentId(departmentId: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/find/${departmentId}/enterprise`);
    return this.authService.attachTokenToRequest(request);
  }

  findDepartmentsByEnterpriseId(enterpriseId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/enterprise/${enterpriseId}/departments`);
    return this.authService.attachTokenToRequest(request);
  }

  findAll(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    return this.authService.attachTokenToRequest(request);
  }

  
}
