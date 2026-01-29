import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatChipsModule } from '@angular/material/chips';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { AdminService, AdminStats } from '../../services/admin.service';
import { User } from '../../models/user.model';
import { Coupon } from '../../models/coupon.model';
import { Category, Product } from '../../models/product.model';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTabsModule,
    MatTableModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
    MatChipsModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTooltipModule
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit {
  stats: AdminStats | null = null;
  users: User[] = [];
  pendingSellers: User[] = [];
  verifiedSellers: User[] = [];
  premiumUsers: User[] = [];
  products: Product[] = [];
  coupons: Coupon[] = [];
  categories: Category[] = [];

  isLoadingStats = true;
  isLoadingUsers = false;
  isLoadingPendingSellers = false;
  isLoadingVerifiedSellers = false;
  isLoadingPremiumUsers = false;
  isLoadingProducts = false;
  isLoadingCoupons = false;
  isLoadingCategories = false;

  // Pagination
  usersTotalElements = 0;
  usersPageSize = 10;
  usersPageIndex = 0;
  selectedRole = '';

  productsTotalElements = 0;
  productsPageSize = 12;
  productsPageIndex = 0;

  // Forms
  couponForm: FormGroup;
  categoryForm: FormGroup;
  showCouponForm = false;
  showCategoryForm = false;
  isSubmitting = false;

  roles = ['USER', 'SELLER', 'ADMIN'];

  constructor(
    private fb: FormBuilder,
    private adminService: AdminService,
    private snackBar: MatSnackBar
  ) {
    this.couponForm = this.fb.group({
      code: ['', [Validators.required, Validators.minLength(4)]],
      discountType: ['PERCENTAGE', [Validators.required]],
      discountValue: ['', [Validators.required, Validators.min(1)]],
      minOrderValue: ['', [Validators.required, Validators.min(0)]],
      maxDiscount: [''],
      validFrom: ['', [Validators.required]],
      validUntil: ['', [Validators.required]],
      usageLimit: ['', [Validators.required, Validators.min(1)]]
    });

    this.categoryForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      description: ['']
    });
  }

  ngOnInit(): void {
    this.loadStats();
    this.loadUsers();
    this.loadPendingSellers();
    this.loadVerifiedSellers();
    this.loadPremiumUsers();
    this.loadProducts();
    this.loadCoupons();
    this.loadCategories();
  }

  loadStats(): void {
    this.isLoadingStats = true;
    this.adminService.getAdminStats().subscribe({
      next: (stats) => {
        this.stats = stats;
        this.isLoadingStats = false;
      },
      error: () => this.isLoadingStats = false
    });
  }

  loadUsers(): void {
    this.isLoadingUsers = true;
    const role = this.selectedRole || undefined;
    this.adminService.getUsers(this.usersPageIndex, this.usersPageSize, role).subscribe({
      next: (response) => {
        this.users = response.content;
        this.usersTotalElements = response.totalElements;
        this.isLoadingUsers = false;
      },
      error: () => {
        this.isLoadingUsers = false;
        this.snackBar.open('Failed to load users', 'Close', { duration: 3000 });
      }
    });
  }

  loadPendingSellers(): void {
    this.isLoadingPendingSellers = true;
    this.adminService.getPendingSellers().subscribe({
      next: (sellers) => {
        this.pendingSellers = sellers;
        this.isLoadingPendingSellers = false;
      },
      error: () => this.isLoadingPendingSellers = false
    });
  }

  loadVerifiedSellers(): void {
    this.isLoadingVerifiedSellers = true;
    this.adminService.getVerifiedSellers().subscribe({
      next: (sellers) => {
        this.verifiedSellers = sellers;
        this.isLoadingVerifiedSellers = false;
      },
      error: () => this.isLoadingVerifiedSellers = false
    });
  }

  loadPremiumUsers(): void {
    this.isLoadingPremiumUsers = true;
    this.adminService.getPremiumUsers().subscribe({
      next: (users) => {
        this.premiumUsers = users;
        this.isLoadingPremiumUsers = false;
      },
      error: () => this.isLoadingPremiumUsers = false
    });
  }

  loadProducts(): void {
    this.isLoadingProducts = true;
    this.adminService.getAllProducts(this.productsPageIndex, this.productsPageSize).subscribe({
      next: (response) => {
        this.products = response?.content || [];
        this.productsTotalElements = response?.totalElements || 0;
        this.isLoadingProducts = false;
      },
      error: () => {
        this.products = [];
        this.isLoadingProducts = false;
      }
    });
  }

  onProductsPageChange(event: PageEvent): void {
    this.productsPageIndex = event.pageIndex;
    this.productsPageSize = event.pageSize;
    this.loadProducts();
  }

  deleteSeller(seller: User): void {
    if (confirm(`Remove seller "${seller.name}"? This will demote them to regular user.`)) {
      this.adminService.deleteSeller(seller.id).subscribe({
        next: () => {
          this.verifiedSellers = this.verifiedSellers.filter(s => s.id !== seller.id);
          this.loadStats();
          this.snackBar.open('Seller removed', 'Close', { duration: 3000 });
        },
        error: () => {
          this.snackBar.open('Failed to remove seller', 'Close', { duration: 3000 });
        }
      });
    }
  }

  grantPremium(user: User): void {
    this.adminService.grantPremium(user.id).subscribe({
      next: () => {
        user.premiumStatus = true;
        this.loadPremiumUsers();
        this.loadStats();
        this.snackBar.open('Premium granted', 'Close', { duration: 3000 });
      },
      error: () => {
        this.snackBar.open('Failed to grant premium', 'Close', { duration: 3000 });
      }
    });
  }

  deleteProduct(product: Product): void {
    const productId = product.id ?? product.productId;
    if (!productId) return;

    if (confirm(`Delete product "${product.name}"?`)) {
      this.adminService.deleteProduct(productId).subscribe({
        next: () => {
          this.products = this.products.filter(p => (p.id ?? p.productId) !== productId);
          this.productsTotalElements--;
          this.snackBar.open('Product deleted', 'Close', { duration: 3000 });
        },
        error: () => {
          this.snackBar.open('Failed to delete product', 'Close', { duration: 3000 });
        }
      });
    }
  }

  getProductId(product: Product): number {
    return product.id ?? product.productId ?? 0;
  }

  getProductStock(product: Product): number {
    return product.stock ?? product.stockQuantity ?? 0;
  }

  loadCoupons(): void {
    this.isLoadingCoupons = true;
    this.adminService.getCoupons().subscribe({
      next: (coupons) => {
        this.coupons = coupons;
        this.isLoadingCoupons = false;
      },
      error: () => this.isLoadingCoupons = false
    });
  }

  loadCategories(): void {
    this.isLoadingCategories = true;
    this.adminService.getCategories().subscribe({
      next: (categories) => {
        this.categories = categories;
        this.isLoadingCategories = false;
      },
      error: () => this.isLoadingCategories = false
    });
  }

  onUsersPageChange(event: PageEvent): void {
    this.usersPageIndex = event.pageIndex;
    this.usersPageSize = event.pageSize;
    this.loadUsers();
  }

  onRoleFilterChange(): void {
    this.usersPageIndex = 0;
    this.loadUsers();
  }

  changeUserRole(user: User, newRole: string): void {
    this.adminService.changeUserRole(user.id, newRole).subscribe({
      next: () => {
        user.role = newRole as any;
        this.snackBar.open('User role updated', 'Close', { duration: 3000 });
      },
      error: () => {
        this.snackBar.open('Failed to update user role', 'Close', { duration: 3000 });
      }
    });
  }

  cancelUserPremium(user: User): void {
    if (confirm(`Cancel premium subscription for ${user.name}?`)) {
      this.adminService.cancelUserPremium(user.id).subscribe({
        next: () => {
          user.premiumStatus = false;
          this.premiumUsers = this.premiumUsers.filter(u => u.id !== user.id);
          this.loadStats();
          this.snackBar.open('Premium subscription cancelled', 'Close', { duration: 3000 });
        },
        error: () => {
          this.snackBar.open('Failed to cancel premium', 'Close', { duration: 3000 });
        }
      });
    }
  }

  approveSeller(user: User): void {
    this.adminService.approveSeller(user.id).subscribe({
      next: () => {
        this.pendingSellers = this.pendingSellers.filter(s => s.id !== user.id);
        this.loadVerifiedSellers();
        this.loadStats();
        this.snackBar.open('Seller approved', 'Close', { duration: 3000 });
      },
      error: () => {
        this.snackBar.open('Failed to approve seller', 'Close', { duration: 3000 });
      }
    });
  }

  rejectSeller(user: User): void {
    if (confirm(`Reject seller application from ${user.name}?`)) {
      this.adminService.rejectSeller(user.id).subscribe({
        next: () => {
          this.pendingSellers = this.pendingSellers.filter(s => s.id !== user.id);
          this.snackBar.open('Seller rejected', 'Close', { duration: 3000 });
        },
        error: () => {
          this.snackBar.open('Failed to reject seller', 'Close', { duration: 3000 });
        }
      });
    }
  }

  createCoupon(): void {
    if (this.couponForm.invalid) {
      this.couponForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    this.adminService.createCoupon(this.couponForm.value).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.showCouponForm = false;
        this.couponForm.reset({ discountType: 'PERCENTAGE' });
        this.loadCoupons();
        this.snackBar.open('Coupon created', 'Close', { duration: 3000 });
      },
      error: () => {
        this.isSubmitting = false;
        this.snackBar.open('Failed to create coupon', 'Close', { duration: 3000 });
      }
    });
  }

  deleteCoupon(coupon: Coupon): void {
    if (confirm(`Delete coupon "${coupon.code}"?`)) {
      this.adminService.deleteCoupon(coupon.id).subscribe({
        next: () => {
          this.coupons = this.coupons.filter(c => c.id !== coupon.id);
          this.snackBar.open('Coupon deleted', 'Close', { duration: 3000 });
        },
        error: () => {
          this.snackBar.open('Failed to delete coupon', 'Close', { duration: 3000 });
        }
      });
    }
  }

  addCategory(): void {
    if (this.categoryForm.invalid) {
      this.categoryForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    this.adminService.addCategory(this.categoryForm.value).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.showCategoryForm = false;
        this.categoryForm.reset();
        this.loadCategories();
        this.snackBar.open('Category added', 'Close', { duration: 3000 });
      },
      error: () => {
        this.isSubmitting = false;
        this.snackBar.open('Failed to add category', 'Close', { duration: 3000 });
      }
    });
  }

  deleteCategory(category: Category): void {
    if (confirm(`Delete category "${category.name}"? This may affect products in this category.`)) {
      this.adminService.deleteCategory(category.id).subscribe({
        next: () => {
          this.categories = this.categories.filter(c => c.id !== category.id);
          this.snackBar.open('Category deleted', 'Close', { duration: 3000 });
        },
        error: () => {
          this.snackBar.open('Failed to delete category', 'Close', { duration: 3000 });
        }
      });
    }
  }
}
