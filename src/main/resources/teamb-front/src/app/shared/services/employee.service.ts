import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  
  private baseUrl = 'http://localhost:8082/api/employees';


  constructor(private http: HttpClient) { }

 
  registerEmployee(request: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/register/employee`, request);
  }

  findAllEmployees(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    return request;
  }

  findEmployeeById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/${id}`);
    return request;
  }

  deleteEmployeeById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    return request;
  }

  updateEmployee(employeeDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${employeeDTO.id}`, employeeDTO);
    return request;
  }

  associateEmployeeWithAddress(addressId: number, employeeId: number): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/${employeeId}/address/${addressId}`, {});
    return request;
  }

  disassociateEmployeeFromAddress(addressId: number): Observable<any> {
    const request = this.http.delete<any>(`${this.baseUrl}/${addressId}/disassociate`);
    return request;
  }

  findEmployeesByDepartmentId(departmentId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/department/find/all/${departmentId}`);
    return request;
  }

  assignDepartmentToEmployee(employeeId: number, departmentId: number): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/${employeeId}/department/${departmentId}`, {});
    return request;
  }

  unassignDepartmentFromEmployee(employeeId: number): Observable<any> {
    const request = this.http.delete<any>(`${this.baseUrl}/${employeeId}/unassign`);
    return request;
  }

  
}
