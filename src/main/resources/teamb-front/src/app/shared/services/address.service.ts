import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AddressService {
 
  private baseUrl = 'http://localhost:8082/api/addresses';

  constructor(private http: HttpClient) { }


  findAllAddresses(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    return request;
  }

  findAddressById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/get/${id}`);
    return request;
  }

  saveAddress(addressDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, addressDTO);
    return request;
  }

  updateAddress(addressDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${addressDTO.id}`, addressDTO);
    return request;
  }

  deleteAddressById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    return request;
  }

  findAddressesByEmployeeId(employeeId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/employee/${employeeId}`);
    return request;
  }

  associateEmployeeWithAddress(addressId: number, employeeId: number): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/${addressId}/employee/${employeeId}`, {});
    return request;
  }

  disassociateEmployeeFromAddress(addressId: number): Observable<any> {
    const request = this.http.delete<any>(`${this.baseUrl}/${addressId}/employee`);
    return request;
  }

  findAll(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    return request;
  }

  
}
