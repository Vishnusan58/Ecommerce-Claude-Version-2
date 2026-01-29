import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User } from '../models/user.model';
import { Order } from '../models/order.model';
import { Category, PageResponse, Product } from '../models/product.model';
import { Coupon, CreateCouponRequest } from '../models/coupon.model';

export interface AdminStats {
  totalRevenue: number;
  totalOrders: number;
  totalUsers: number;
  premiumUsers: number;
  totalSellers: number;
  pendingSellers: number;
}

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private readonly API_URL = '/api/admin';

  constructor(private http: HttpClient) {}

  getAdminStats(): Observable<AdminStats> {
    return this.http.get<AdminStats>(`${this.API_URL}/analytics`).pipe(
      catchError(() => of({
        totalRevenue: 0,
        totalOrders: 0,
        totalUsers: 0,
        premiumUsers: 0,
        totalSellers: 0,
        pendingSellers: 0
      }))
    );
  }

  // User Management
  getUsers(page: number = 0, size: number = 10, role?: string): Observable<PageResponse<User>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (role) {
      params = params.set('role', role);
    }

    return this.http.get<PageResponse<User>>(`${this.API_URL}/users`, { params });
  }

  changeUserRole(userId: number, role: string): Observable<User> {
    return this.http.put<User>(`${this.API_URL}/users/${userId}/role`, { role });
  }

  cancelUserPremium(userId: number): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.API_URL}/users/${userId}/premium`);
  }

  // Seller Management
  getPendingSellers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.API_URL}/users/sellers/pending`).pipe(
      catchError(() => of([]))
    );
  }

  approveSeller(userId: number): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`${this.API_URL}/users/sellers/${userId}/approve`, {});
  }

  rejectSeller(userId: number): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`${this.API_URL}/users/sellers/${userId}/reject`, {});
  }

  getVerifiedSellers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.API_URL}/users/sellers`).pipe(
      catchError(() => of([]))
    );
  }

  deleteSeller(userId: number): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.API_URL}/users/sellers/${userId}`);
  }

  // Premium Management
  grantPremium(userId: number): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`${this.API_URL}/users/${userId}/premium`, {});
  }

  getPremiumUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.API_URL}/users/premium`).pipe(
      catchError(() => of([]))
    );
  }

  // Product Management
  getAllProducts(page: number = 0, size: number = 10): Observable<PageResponse<Product>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<PageResponse<Product>>(`${this.API_URL}/products`, { params }).pipe(
      catchError(() => of({
        content: [],
        totalElements: 0,
        totalPages: 0,
        size: size,
        number: page,
        first: true,
        last: true
      }))
    );
  }

  deleteProduct(productId: number): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.API_URL}/products/${productId}`);
  }

  // Order Management
  getAllOrders(page: number = 0, size: number = 10): Observable<PageResponse<Order>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<PageResponse<Order>>(`${this.API_URL}/orders`, { params });
  }

  // Coupon Management
  getCoupons(): Observable<Coupon[]> {
    return this.http.get<Coupon[]>(`${this.API_URL}/coupons`);
  }

  createCoupon(data: CreateCouponRequest): Observable<Coupon> {
    return this.http.post<Coupon>(`${this.API_URL}/coupons`, data);
  }

  deleteCoupon(id: number): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.API_URL}/coupons/${id}`);
  }

  toggleCouponStatus(id: number): Observable<Coupon> {
    return this.http.put<Coupon>(`${this.API_URL}/coupons/${id}/toggle`, {});
  }

  // Category Management
  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.API_URL}/categories`);
  }

  addCategory(data: { name: string; description?: string }): Observable<Category> {
    return this.http.post<Category>(`${this.API_URL}/categories`, data);
  }

  updateCategory(id: number, data: { name: string; description?: string }): Observable<Category> {
    return this.http.put<Category>(`${this.API_URL}/categories/${id}`, data);
  }

  deleteCategory(id: number): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(`${this.API_URL}/categories/${id}`);
  }
}
