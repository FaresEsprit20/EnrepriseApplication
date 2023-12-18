import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService implements OnDestroy {
  private baseUrl = 'http://localhost:8082/api/employees';
  private subscriptions: Subscription[] = [];

  constructor(private http: HttpClient) { }

  ngOnDestroy(): void {
    this.unsubscribeAll();
  }

  private unsubscribeAll(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe());
    this.subscriptions = [];
  }

  private addSubscription(subscription: Subscription): void {
    this.subscriptions.push(subscription);
  }

  findAllEmployees(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findEmployeeById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/${id}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  saveEmployee(employeeDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, employeeDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  deleteEmployeeById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  updateEmployee(employeeDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${employeeDTO.id}`, employeeDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  associateEmployeeWithAddress(addressId: number, employeeId: number): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/${employeeId}/address/${addressId}`, {});
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  disassociateEmployeeFromAddress(addressId: number): Observable<any> {
    const request = this.http.delete<any>(`${this.baseUrl}/${addressId}/disassociate`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findEmployeesByDepartmentId(departmentId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/department/find/all/${departmentId}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  assignDepartmentToEmployee(employeeId: number, departmentId: number): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/${employeeId}/department/${departmentId}`, {});
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  unassignDepartmentFromEmployee(employeeId: number): Observable<any> {
    const request = this.http.delete<any>(`${this.baseUrl}/${employeeId}/unassign`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  
}
