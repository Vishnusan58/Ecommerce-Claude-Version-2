import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';
import { AuthService } from '../../services/auth.service';
import { Product } from '../../models/product.model';

@Component({
  selector: 'app-compare',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTableModule
  ],
  templateUrl: './compare.component.html',
  styleUrl: './compare.component.css'
})
export class CompareComponent implements OnInit {
  product1: Product | null = null;
  product2: Product | null = null;
  isLoading = true;

  comparisonRows = [
    { label: 'Price', key: 'price', format: 'currency' },
    { label: 'Rating', key: 'rating', format: 'rating' },
    { label: 'Reviews', key: 'reviewCount', format: 'number' },
    { label: 'Brand', key: 'brand', format: 'text' },
    { label: 'Category', key: 'category', format: 'category' },
    { label: 'Stock', key: 'stock', format: 'stock' },
    { label: 'Premium Early Access', key: 'premiumEarlyAccess', format: 'boolean' }
  ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService,
    private cartService: CartService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const id1 = this.route.snapshot.queryParams['id1'];
    const id2 = this.route.snapshot.queryParams['id2'];

    if (!id1 || !id2) {
      this.snackBar.open('Please select two products to compare', 'Close', { duration: 3000 });
      this.router.navigate(['/products']);
      return;
    }

    this.loadProducts(+id1, +id2);
  }

  loadProducts(id1: number, id2: number): void {
    this.isLoading = true;
    this.productService.compareProducts(id1, id2).subscribe({
      next: (response) => {
        this.product1 = response.product1;
        this.product2 = response.product2;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.snackBar.open('Failed to load products', 'Close', { duration: 3000 });
        this.router.navigate(['/products']);
      }
    });
  }

  getValue(product: Product, key: string): any {
    if (key === 'category') {
      return product.category.name;
    }
    return (product as any)[key];
  }

  formatValue(value: any, format: string): string {
    switch (format) {
      case 'currency':
        return '₹' + value.toLocaleString();
      case 'rating':
        return value + '/5';
      case 'number':
        return value.toLocaleString();
      case 'stock':
        return value > 0 ? `${value} in stock` : 'Out of stock';
      case 'boolean':
        return value ? 'Yes' : 'No';
      default:
        return value;
    }
  }

  isBetter(key: string, val1: any, val2: any): 'product1' | 'product2' | 'equal' {
    if (val1 === val2) return 'equal';

    switch (key) {
      case 'price':
        return val1 < val2 ? 'product1' : 'product2'; // Lower is better
      case 'rating':
      case 'reviewCount':
      case 'stock':
        return val1 > val2 ? 'product1' : 'product2'; // Higher is better
      default:
        return 'equal';
    }
  }

  addToCart(product: Product): void {
    if (!this.authService.isLoggedIn()) {
      this.snackBar.open('Please login to add items to cart', 'Login', { duration: 3000 })
        .onAction().subscribe(() => this.router.navigate(['/login']));
      return;
    }

    this.cartService.addToCart(product.id).subscribe({
      next: () => {
        this.snackBar.open('Added to cart!', 'View Cart', { duration: 3000 })
          .onAction().subscribe(() => this.router.navigate(['/cart']));
      },
      error: () => {
        this.snackBar.open('Failed to add to cart', 'Close', { duration: 3000 });
      }
    });
  }

  viewProduct(productId: number): void {
    this.router.navigate(['/products', productId]);
  }
}
