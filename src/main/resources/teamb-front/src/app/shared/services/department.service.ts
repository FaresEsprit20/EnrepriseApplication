import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService implements OnDestroy {
  private baseUrl = 'http://localhost:8082/api/departments';
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

  findAllDepartments(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findDepartmentById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/find/${id}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  saveDepartment(departmentDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, departmentDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  deleteDepartmentById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  updateDepartment(departmentDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentDTO.id}`, departmentDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findEmployeesByDepartmentId(departmentId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/${departmentId}/employees`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  addEmployeeToDepartment(departmentId: number, employeeDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentId}/add-employee`, employeeDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  removeEmployeeFromDepartment(departmentId: number, employeeId: number): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentId}/remove-employee/${employeeId}`, {});
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  associateDepartmentWithEnterprise(enterpriseId: number, departmentId: number): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentId}/associate-enterprise/${enterpriseId}`, {});
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  disassociateDepartmentFromEnterprise(departmentId: number): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${departmentId}/disassociate-enterprise`, {});
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  getEnterpriseByDepartmentId(departmentId: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/find/${departmentId}/enterprise`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findDepartmentsByEnterpriseId(enterpriseId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/enterprise/${enterpriseId}/departments`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findAll(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }
  
}
