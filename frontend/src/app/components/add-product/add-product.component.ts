import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { SellerService } from '../../services/seller.service';
import { ProductService } from '../../services/product.service';
import { Category } from '../../models/product.model';

@Component({
  selector: 'app-add-product',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
    MatProgressSpinnerModule,
    MatSnackBarModule
  ],
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.css'
})
export class AddProductComponent implements OnInit {
  @Output() productAdded = new EventEmitter<void>();

  productForm: FormGroup;
  categories: Category[] = [];
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private sellerService: SellerService,
    private productService: ProductService,
    private snackBar: MatSnackBar
  ) {
    this.productForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(10)]],
      price: ['', [Validators.required, Validators.min(1)]],
      originalPrice: [''],
      imageUrl: ['', [Validators.required]],
      categoryId: ['', [Validators.required]],
      brand: ['', [Validators.required]],
      stock: ['', [Validators.required, Validators.min(0)]],
      premiumEarlyAccess: [false]
    });
  }

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.productService.getCategories().subscribe({
      next: (categories) => this.categories = categories,
      error: () => this.snackBar.open('Failed to load categories', 'Close', { duration: 3000 })
    });
  }

  onSubmit(): void {
    if (this.productForm.invalid) {
      this.productForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    const formValue = this.productForm.value;

    this.sellerService.addProduct({
      ...formValue,
      originalPrice: formValue.originalPrice || null
    }).subscribe({
      next: () => {
        this.isLoading = false;
        this.productForm.reset();
        this.productAdded.emit();
      },
      error: (error) => {
        this.isLoading = false;
        const message = error.error?.message || 'Failed to add product';
        this.snackBar.open(message, 'Close', { duration: 3000 });
      }
    });
  }

  getErrorMessage(field: string): string {
    const control = this.productForm.get(field);
    if (control?.hasError('required')) {
      return 'This field is required';
    }
    if (control?.hasError('minlength')) {
      const minLength = control.errors?.['minlength'].requiredLength;
      return `Minimum ${minLength} characters required`;
    }
    if (control?.hasError('min')) {
      return 'Value must be greater than 0';
    }
    return '';
  }
}
