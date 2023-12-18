import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ResponsibleService implements OnDestroy {
  private baseUrl = 'http://localhost:8082/api/responsibles';
  private subscriptions: Subscription[] = [];

  constructor(private http: HttpClient) {}

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

  findAllResponsibles(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/all`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findResponsibleById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/get/${id}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findAllResponsibleByNameOrLastName(name: string, lastName: string): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/search?name=${name}&lastName=${lastName}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  saveResponsible(responsibleDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, responsibleDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  deleteResponsibleById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  updateResponsible(responsibleDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${responsibleDTO.id}`, responsibleDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }
  
}
