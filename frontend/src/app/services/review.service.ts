import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Review, CreateReviewRequest } from '../models/review.model';
import { PageResponse } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private readonly API_URL = '/api/user/reviews';

  constructor(private http: HttpClient) {}

  getReviews(productId: number, page: number = 0, size: number = 10): Observable<PageResponse<Review>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<PageResponse<Review>>(`${this.API_URL}/${productId}`, { params }).pipe(
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

  addReview(productId: number, review: CreateReviewRequest): Observable<Review> {
    return this.http.post<Review>(`${this.API_URL}/${productId}`, review);
  }

  canReview(productId: number): Observable<{ canReview: boolean }> {
    return this.http.get<{ canReview: boolean }>(`${this.API_URL}/${productId}/can-review`).pipe(
      catchError(() => of({ canReview: false }))
    );
  }
}
