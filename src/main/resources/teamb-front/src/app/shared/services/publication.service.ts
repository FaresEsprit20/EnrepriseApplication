import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PublicationService implements OnDestroy {
  private baseUrl = 'http://localhost:8082/api/publications';
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

  findAllPublications(): Observable<any[]> {
    const headers = { 'Authorization': `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbXAxQGdtYWlsLmNvbSIsImlhdCI6MTcwMjgzMDMwNSwiZXhwIjoxNzAyODczNTA1fQ.s4VpXUuV7nTVWKFKnGTsyuz0VyMjcK1uCM40SP-3h0g` }
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findPublicationById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/get/${id}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findAllByEmployeeId(employeeId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/employee/${employeeId}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  createPublication(publicationDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, publicationDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  updatePublication(publicationId: number, publicationDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${publicationId}`, publicationDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  deletePublication(publicationId: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${publicationId}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findEmployeeByPublicationId(publicationId: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/${publicationId}/employee`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  associateEmployeeWithPublication(publicationId: number, employeeId: number): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/${publicationId}/employee/${employeeId}`, {});
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  disassociateEmployeeFromPublication(publicationId: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/${publicationId}/employee`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  getRatingForPublication(publicationId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/${publicationId}/rating`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  
}
