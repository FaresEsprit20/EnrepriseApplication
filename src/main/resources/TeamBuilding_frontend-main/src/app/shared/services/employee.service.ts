import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private baseUrl = 'http://localhost:8082/api/employees';

  constructor(private http: HttpClient, private authService: AuthService) {}

  findAllEmployees(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    return this.authService.attachTokenToRequest(request);
  }

  findEmployeeById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/${id}`);
    return this.authService.attachTokenToRequest(request);
  }

  saveEmployee(employeeDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, employeeDTO);
    return this.authService.attachTokenToRequest(request);
  }

  deleteEmployeeById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    return this.authService.attachTokenToRequest(request);
  }

  updateEmployee(employeeDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${employeeDTO.id}`, employeeDTO);
    return this.authService.attachTokenToRequest(request);
  }

  associateEmployeeWithAddress(addressId: number, employeeId: number): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/${employeeId}/address/${addressId}`, {});
    return this.authService.attachTokenToRequest(request);
  }

  disassociateEmployeeFromAddress(addressId: number): Observable<any> {
    const request = this.http.delete<any>(`${this.baseUrl}/${addressId}/disassociate`);
    return this.authService.attachTokenToRequest(request);
  }

  findEmployeesByDepartmentId(departmentId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/department/find/all/${departmentId}`);
    return this.authService.attachTokenToRequest(request);
  }

  assignDepartmentToEmployee(employeeId: number, departmentId: number): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/${employeeId}/department/${departmentId}`, {});
    return this.authService.attachTokenToRequest(request);
  }

  unassignDepartmentFromEmployee(employeeId: number): Observable<any> {
    const request = this.http.delete<any>(`${this.baseUrl}/${employeeId}/unassign`);
    return this.authService.attachTokenToRequest(request);
  }

  
}
