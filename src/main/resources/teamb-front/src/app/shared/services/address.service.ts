import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AddressService implements OnDestroy {
  private baseUrl = 'http://localhost:8082/api/addresses';
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

  findAllAddresses(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findAddressById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/get/${id}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  saveAddress(addressDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, addressDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  updateAddress(addressDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${addressDTO.id}`, addressDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  deleteAddressById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findAddressesByEmployeeId(employeeId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/employee/${employeeId}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  associateEmployeeWithAddress(addressId: number, employeeId: number): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/${addressId}/employee/${employeeId}`, {});
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  disassociateEmployeeFromAddress(addressId: number): Observable<any> {
    const request = this.http.delete<any>(`${this.baseUrl}/${addressId}/employee`);
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
