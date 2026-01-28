import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTabsModule } from '@angular/material/tabs';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';
import { WishlistService } from '../../services/wishlist.service';
import { AuthService } from '../../services/auth.service';
import { Product } from '../../models/product.model';
import { ReviewsComponent } from '../reviews/reviews.component';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTabsModule,
    MatFormFieldModule,
    MatInputModule,
    ReviewsComponent
  ],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent implements OnInit {
  product: Product | null = null;
  isLoading = true;
  quantity = 1;
  selectedImage: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService,
    private cartService: CartService,
    private wishlistService: WishlistService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const productId = this.route.snapshot.params['id'];
    if (productId) {
      this.loadProduct(+productId);
    }
  }

  loadProduct(id: number): void {
    this.isLoading = true;
    this.productService.getProductById(id).subscribe({
      next: (product) => {
        this.product = product;
        this.selectedImage = product.imageUrl;
        this.isLoading = false;
        this.productService.addToRecentlyViewed(product);
      },
      error: () => {
        this.isLoading = false;
        this.snackBar.open('Product not found', 'Close', { duration: 3000 });
        this.router.navigate(['/products']);
      }
    });
  }

  selectImage(imageUrl: string): void {
    this.selectedImage = imageUrl;
  }

  incrementQuantity(): void {
    if (this.product && this.quantity < this.product.stock) {
      this.quantity++;
    }
  }

  decrementQuantity(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  addToCart(): void {
    if (!this.authService.isLoggedIn()) {
      this.snackBar.open('Please login to add items to cart', 'Login', { duration: 3000 })
        .onAction().subscribe(() => this.router.navigate(['/login']));
      return;
    }

    if (this.product) {
      this.cartService.addToCart(this.product.id, this.quantity).subscribe({
        next: () => {
          this.snackBar.open(`Added ${this.quantity} item(s) to cart!`, 'View Cart', { duration: 3000 })
            .onAction().subscribe(() => this.router.navigate(['/cart']));
        },
        error: () => {
          this.snackBar.open('Failed to add to cart', 'Close', { duration: 3000 });
        }
      });
    }
  }

  toggleWishlist(): void {
    if (!this.authService.isLoggedIn()) {
      this.snackBar.open('Please login to add items to wishlist', 'Login', { duration: 3000 })
        .onAction().subscribe(() => this.router.navigate(['/login']));
      return;
    }

    if (this.product) {
      if (this.isInWishlist) {
        this.wishlistService.removeFromWishlist(this.product.id).subscribe({
          next: () => this.snackBar.open('Removed from wishlist', 'Close', { duration: 2000 })
        });
      } else {
        this.wishlistService.addToWishlist(this.product.id).subscribe({
          next: () => this.snackBar.open('Added to wishlist!', 'Close', { duration: 2000 })
        });
      }
    }
  }

  get isInWishlist(): boolean {
    return this.product ? this.wishlistService.isInWishlist(this.product.id) : false;
  }

  get discountPercent(): number {
    if (this.product?.originalPrice && this.product.originalPrice > this.product.price) {
      return Math.round((1 - this.product.price / this.product.originalPrice) * 100);
    }
    return 0;
  }

  get allImages(): string[] {
    if (!this.product) return [];
    const images = [this.product.imageUrl];
    if (this.product.images) {
      images.push(...this.product.images);
    }
    return images;
  }
}
