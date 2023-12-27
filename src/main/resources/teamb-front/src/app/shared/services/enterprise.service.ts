import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EnterpriseService implements OnDestroy {
  private baseUrl = 'http://localhost:8082/api/enterprises';
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

  findAllEnterprises(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findEnterpriseById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/get/${id}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  saveEnterprise(enterpriseDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, enterpriseDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  updateEnterprise(enterpriseDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${enterpriseDTO.id}`, enterpriseDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  deleteEnterpriseById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findDepartmentsByEnterpriseId(enterpriseId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/departments/${enterpriseId}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  associateDepartmentWithEnterprise(enterpriseId: number, departmentDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/departments/${enterpriseId}`, departmentDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  disassociateDepartmentFromEnterprise(departmentId: number): Observable<any> {
    const request = this.http.delete<any>(`${this.baseUrl}/departments/${departmentId}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findEnterpriseByDepartmentId(departmentId: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/departments/${departmentId}/enterprise`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  
}
