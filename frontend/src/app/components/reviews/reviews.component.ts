import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { ReviewService } from '../../services/review.service';
import { AuthService } from '../../services/auth.service';
import { Review } from '../../models/review.model';

@Component({
  selector: 'app-reviews',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatPaginatorModule
  ],
  templateUrl: './reviews.component.html',
  styleUrl: './reviews.component.css'
})
export class ReviewsComponent implements OnInit {
  @Input() productId!: number;

  reviews: Review[] = [];
  isLoading = true;
  canReview = false;
  isSubmitting = false;

  totalElements = 0;
  pageSize = 5;
  pageIndex = 0;

  reviewForm: FormGroup;
  selectedRating = 0;
  hoverRating = 0;

  constructor(
    private fb: FormBuilder,
    private reviewService: ReviewService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {
    this.reviewForm = this.fb.group({
      rating: [0, [Validators.required, Validators.min(1), Validators.max(5)]],
      comment: ['', [Validators.required, Validators.minLength(10)]]
    });
  }

  ngOnInit(): void {
    this.loadReviews();
    if (this.authService.isLoggedIn()) {
      this.checkCanReview();
    }
  }

  loadReviews(): void {
    this.isLoading = true;
    this.reviewService.getReviews(this.productId, this.pageIndex, this.pageSize).subscribe({
      next: (response) => {
        this.reviews = response.content;
        this.totalElements = response.totalElements;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }

  checkCanReview(): void {
    this.reviewService.canReview(this.productId).subscribe({
      next: (response) => {
        this.canReview = response.canReview;
      }
    });
  }

  setRating(rating: number): void {
    this.selectedRating = rating;
    this.reviewForm.patchValue({ rating });
  }

  submitReview(): void {
    if (this.reviewForm.invalid) {
      if (this.selectedRating === 0) {
        this.snackBar.open('Please select a rating', 'Close', { duration: 3000 });
      }
      return;
    }

    this.isSubmitting = true;
    this.reviewService.addReview(this.productId, this.reviewForm.value).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.canReview = false;
        this.reviewForm.reset();
        this.selectedRating = 0;
        this.snackBar.open('Review submitted successfully!', 'Close', { duration: 3000 });
        this.loadReviews();
      },
      error: (error) => {
        this.isSubmitting = false;
        const message = error.error?.message || 'Failed to submit review';
        this.snackBar.open(message, 'Close', { duration: 3000 });
      }
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadReviews();
  }

  get isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }
}
