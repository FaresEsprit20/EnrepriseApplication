import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RatingService implements OnDestroy {
  private baseUrl = 'http://localhost:8082/api/ratings';
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

  findRatingsByEmployeeId(employeeId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/employee/${employeeId}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  findRatingsByPublicationId(publicationId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/publication/${publicationId}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  createRating(publicationId: number, employeeId: number, value: boolean): Observable<any> {
    const ratingDTO = { publicationId, employeeId, value };
    const request = this.http.post<any>(`${this.baseUrl}/create`, ratingDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  updateRating(ratingId: number, value: boolean, employeeId: number): Observable<any> {
    const ratingDTO = { ratingId, value, employeeId };
    const request = this.http.put<any>(`${this.baseUrl}/update/${ratingId}`, ratingDTO);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  deleteRating(ratingId: number, employeeId: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${ratingId}?employeeId=${employeeId}`);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  upvotePublication(publicationId: number): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/upvote/${publicationId}`, null);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  downvotePublication(publicationId: number): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/downvote/${publicationId}`, null);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }

  getVoteCounts(publicationId: number): Observable<RatingCountDTO> {
    const url = `${this.baseUrl}/publication/${publicationId}/votes/count`;
    const request = this.http.get<RatingCountDTO>(url);
    const subscription = request.subscribe();
    this.addSubscription(subscription);
    return request;
  }
}

export interface RatingCountDTO {
  upVotes: number;
  downVotes: number;
}
