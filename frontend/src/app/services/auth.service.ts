import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { User, LoginRequest, RegisterRequest, AuthResponse } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_URL = '/api/auth';
  private readonly TOKEN_KEY = 'auth_token';
  private readonly USER_KEY = 'current_user';

  private currentUserSubject = new BehaviorSubject<User | null>(this.getStoredUser());
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API_URL}/login`, credentials).pipe(
      tap(response => {
        this.storeToken(response.token);
        this.storeUser(response.user);
        this.currentUserSubject.next(response.user);
      })
    );
  }

  register(userData: RegisterRequest): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`${this.API_URL}/register`, userData);
  }

  logout(): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`${this.API_URL}/logout`, {}).pipe(
      tap(() => {
        this.clearStorage();
        this.currentUserSubject.next(null);
      })
    );
  }

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${this.API_URL}/me`).pipe(
      tap(user => {
        this.storeUser(user);
        this.currentUserSubject.next(user);
      })
    );
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getCurrentUserValue(): User | null {
    return this.currentUserSubject.value;
  }

  hasRole(role: string): boolean {
    const user = this.getCurrentUserValue();
    return user?.role === role;
  }

  isPremium(): boolean {
    const user = this.getCurrentUserValue();
    return user?.premiumStatus ?? false;
  }

  private storeToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  private storeUser(user: User): void {
    localStorage.setItem(this.USER_KEY, JSON.stringify(user));
  }

  private getStoredUser(): User | null {
    const userJson = localStorage.getItem(this.USER_KEY);
    return userJson ? JSON.parse(userJson) : null;
  }

  private clearStorage(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
  }
}
