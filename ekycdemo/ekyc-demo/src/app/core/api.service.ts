import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {User} from '../model/user.model';
import {Observable} from 'rxjs/index';
import {ApiResponse} from '../model/api.response';
import {LocalStorageService} from './local-storage.service';

export const LOGGED_USER = 'loggedUser';
export const TOKEN = 'token';

@Injectable()
export class ApiService {

  constructor(private http: HttpClient,
              private localStorage: LocalStorageService) { }
  baseUrl = 'http://localhost:8080/users/';

  login(loginPayload): Observable<ApiResponse> {
    return this.http.post<ApiResponse>('http://localhost:8080/' + 'token/generate-token', loginPayload);
  }

  getUsers(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl);
  }

  getUserById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + id);
  }

  createUser(user: User): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl, user);
  }

  updateUser(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl + user.id, user);
  }

  deleteUser(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + id);
  }

  getLoggedUser(): User {
    return this.localStorage.getObject(LOGGED_USER);
  }

  getUserToken() {
    return this.localStorage.getItem(TOKEN);
  }

  storeUserLocal(user: User) {
    this.localStorage.setItem(TOKEN, user.token);
    this.localStorage.setObject(LOGGED_USER, user);
  }

  clearSession(): void {
    this.localStorage.clear();
  }
}
