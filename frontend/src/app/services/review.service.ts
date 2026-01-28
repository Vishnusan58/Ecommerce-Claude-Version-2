import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Review, CreateReviewRequest } from '../models/review.model';
import { PageResponse } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private readonly API_URL = '/api/products';

  constructor(private http: HttpClient) {}

  getReviews(productId: number, page: number = 0, size: number = 10): Observable<PageResponse<Review>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<PageResponse<Review>>(`${this.API_URL}/${productId}/reviews`, { params });
  }

  addReview(productId: number, review: CreateReviewRequest): Observable<Review> {
    return this.http.post<Review>(`${this.API_URL}/${productId}/reviews`, review);
  }

  canReview(productId: number): Observable<{ canReview: boolean }> {
    return this.http.get<{ canReview: boolean }>(`${this.API_URL}/${productId}/can-review`);
  }
}
