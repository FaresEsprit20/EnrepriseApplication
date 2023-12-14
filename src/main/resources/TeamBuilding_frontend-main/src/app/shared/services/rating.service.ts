import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class RatingService {

  private baseUrl = 'http://localhost:8082/api/ratings';

  constructor(private http: HttpClient, private authService: AuthService) { }

  findRatingsByEmployeeId(employeeId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/employee/${employeeId}`);
    return this.authService.attachTokenToRequest(request);
  }

  findRatingsByPublicationId(publicationId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/publication/${publicationId}`);
    return this.authService.attachTokenToRequest(request);
  }

  createRating(publicationId: number, employeeId: number, value: boolean): Observable<any> {
    const ratingDTO = { publicationId, employeeId, value };
    const request = this.http.post<any>(`${this.baseUrl}/create`, ratingDTO);
    return this.authService.attachTokenToRequest(request);
  }

  updateRating(ratingId: number, value: boolean, employeeId: number): Observable<any> {
    const ratingDTO = { ratingId, value, employeeId };
    const request = this.http.put<any>(`${this.baseUrl}/update/${ratingId}`, ratingDTO);
    return this.authService.attachTokenToRequest(request);
  }

  deleteRating(ratingId: number, employeeId: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${ratingId}?employeeId=${employeeId}`);
    return this.authService.attachTokenToRequest(request);
  }

  upvotePublication(publicationId: number): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/upvote/${publicationId}`, null);
    return this.authService.attachTokenToRequest(request);
  }

  downvotePublication(publicationId: number): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/downvote/${publicationId}`, null);
    return this.authService.attachTokenToRequest(request);
  }

  getVoteCounts(publicationId: number): Observable<RatingCountDTO> {
    const url = `${this.baseUrl}/publication/${publicationId}/votes/count`;
    return this.http.get<RatingCountDTO>(url);
  }


}

export interface RatingCountDTO {
  upVotes: number;
  downVotes: number;
}
