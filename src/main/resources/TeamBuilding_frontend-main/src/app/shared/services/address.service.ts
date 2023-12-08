import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private baseUrl = 'http://localhost:8082/api/addresses';

  constructor(private http: HttpClient, private authService: AuthService) { }


  findAllAddresses(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    return this.authService.attachTokenToRequest(request);
  }

  findAddressById(id: number): Observable<any> {
    const request = this.http.get<any>(`${this.baseUrl}/get/${id}`);
    return this.authService.attachTokenToRequest(request);
  }

  saveAddress(addressDTO: any): Observable<any> {
    const request = this.http.post<any>(`${this.baseUrl}/create`, addressDTO);
    return this.authService.attachTokenToRequest(request);
  }

  updateAddress(addressDTO: any): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/update/${addressDTO.id}`, addressDTO);
    return this.authService.attachTokenToRequest(request);
  }

  deleteAddressById(id: number): Observable<void> {
    const request = this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
    return this.authService.attachTokenToRequest(request);
  }

  findAddressesByEmployeeId(employeeId: number): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/employee/${employeeId}`);
    return this.authService.attachTokenToRequest(request);
  }

  associateEmployeeWithAddress(addressId: number, employeeId: number): Observable<any> {
    const request = this.http.put<any>(`${this.baseUrl}/${addressId}/employee/${employeeId}`, {});
    return this.authService.attachTokenToRequest(request);
  }

  disassociateEmployeeFromAddress(addressId: number): Observable<any> {
    const request = this.http.delete<any>(`${this.baseUrl}/${addressId}/employee`);
    return this.authService.attachTokenToRequest(request);
  }

  findAll(): Observable<any[]> {
    const request = this.http.get<any[]>(`${this.baseUrl}/find/all`);
    return this.authService.attachTokenToRequest(request);
  }




}
