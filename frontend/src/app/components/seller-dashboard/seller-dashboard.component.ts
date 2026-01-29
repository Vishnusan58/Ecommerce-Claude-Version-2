import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import { MatChipsModule } from '@angular/material/chips';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatTooltipModule } from '@angular/material/tooltip';
import { SellerService, SellerStats } from '../../services/seller.service';
import { Product } from '../../models/product.model';
import { Order, OrderStatus } from '../../models/order.model';
import { AddProductComponent } from '../add-product/add-product.component';

@Component({
  selector: 'app-seller-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatTabsModule,
    MatTableModule,
    MatSelectModule,
    MatChipsModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatDialogModule,
    MatTooltipModule,
    AddProductComponent
  ],
  templateUrl: './seller-dashboard.component.html',
  styleUrl: './seller-dashboard.component.css'
})
export class SellerDashboardComponent implements OnInit {
  stats: SellerStats | null = null;
  products: Product[] = [];
  orders: Order[] = [];

  isLoadingStats = true;
  isLoadingProducts = false;
  isLoadingOrders = false;

  // Pagination
  productsTotalElements = 0;
  productsPageSize = 10;
  productsPageIndex = 0;

  ordersTotalElements = 0;
  ordersPageSize = 10;
  ordersPageIndex = 0;
  selectedOrderStatus: OrderStatus | '' = '';

  showAddProduct = false;
  productToEdit: Product | null = null;

  orderStatuses: OrderStatus[] = ['PLACED', 'CONFIRMED', 'SHIPPED', 'DELIVERED'];

  constructor(
    private sellerService: SellerService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadStats();
    this.loadProducts();
    this.loadOrders();
  }

  loadStats(): void {
    this.isLoadingStats = true;
    this.sellerService.getSellerStats().subscribe({
      next: (stats) => {
        this.stats = stats;
        this.isLoadingStats = false;
      },
      error: () => {
        this.isLoadingStats = false;
      }
    });
  }

  loadProducts(): void {
    this.isLoadingProducts = true;
    this.sellerService.getSellerProducts(this.productsPageIndex, this.productsPageSize).subscribe({
      next: (response) => {
        this.products = response?.content || [];
        this.productsTotalElements = response?.totalElements || 0;
        this.isLoadingProducts = false;
      },
      error: () => {
        this.products = [];
        this.productsTotalElements = 0;
        this.isLoadingProducts = false;
        this.snackBar.open('Failed to load products', 'Close', { duration: 3000 });
      }
    });
  }

  loadOrders(): void {
    this.isLoadingOrders = true;
    const status = this.selectedOrderStatus || undefined;
    this.sellerService.getSellerOrders(this.ordersPageIndex, this.ordersPageSize, status).subscribe({
      next: (response) => {
        this.orders = response?.content || [];
        this.ordersTotalElements = response?.totalElements || 0;
        this.isLoadingOrders = false;
      },
      error: () => {
        this.orders = [];
        this.ordersTotalElements = 0;
        this.isLoadingOrders = false;
        this.snackBar.open('Failed to load orders', 'Close', { duration: 3000 });
      }
    });
  }

  getProductId(product: Product): number {
    return product.id ?? product.productId ?? 0;
  }

  getProductStock(product: Product): number {
    return product.stock ?? product.stockQuantity ?? 0;
  }

  getProductCategory(product: Product): string {
    return product.category?.name ?? 'Uncategorized';
  }

  onProductsPageChange(event: PageEvent): void {
    this.productsPageIndex = event.pageIndex;
    this.productsPageSize = event.pageSize;
    this.loadProducts();
  }

  onOrdersPageChange(event: PageEvent): void {
    this.ordersPageIndex = event.pageIndex;
    this.ordersPageSize = event.pageSize;
    this.loadOrders();
  }

  onOrderStatusFilterChange(): void {
    this.ordersPageIndex = 0;
    this.loadOrders();
  }

  toggleAddProduct(): void {
    this.showAddProduct = !this.showAddProduct;
    if (!this.showAddProduct) {
      this.productToEdit = null;
    }
  }

  editProduct(product: Product): void {
    this.productToEdit = product;
    this.showAddProduct = true;
  }

  onProductAdded(): void {
    this.showAddProduct = false;
    this.productToEdit = null;
    this.loadProducts();
    this.loadStats();
    this.snackBar.open('Product added successfully!', 'Close', { duration: 3000 });
  }

  onProductUpdated(): void {
    this.showAddProduct = false;
    this.productToEdit = null;
    this.loadProducts();
    this.snackBar.open('Product updated successfully!', 'Close', { duration: 3000 });
  }

  onEditCancelled(): void {
    this.showAddProduct = false;
    this.productToEdit = null;
  }

  deleteProduct(product: Product): void {
    const productId = this.getProductId(product);
    if (!productId) return;

    if (confirm(`Are you sure you want to delete "${product.name}"?`)) {
      this.sellerService.deleteProduct(productId).subscribe({
        next: () => {
          this.loadProducts();
          this.loadStats();
          this.snackBar.open('Product deleted', 'Close', { duration: 3000 });
        },
        error: () => {
          this.snackBar.open('Failed to delete product', 'Close', { duration: 3000 });
        }
      });
    }
  }

  confirmOrder(order: Order): void {
    if (order.status !== 'PLACED') {
      this.snackBar.open('Order can only be confirmed when in PLACED status', 'Close', { duration: 3000 });
      return;
    }

    this.sellerService.updateOrderStatus(order.id, 'CONFIRMED').subscribe({
      next: () => {
        order.status = 'CONFIRMED';
        this.loadStats();
        this.snackBar.open('Order confirmed successfully', 'Close', { duration: 3000 });
      },
      error: () => {
        this.snackBar.open('Failed to confirm order', 'Close', { duration: 3000 });
      }
    });
  }

  markAsShipped(order: Order): void {
    if (order.status !== 'CONFIRMED') {
      this.snackBar.open('Order can only be shipped when in CONFIRMED status', 'Close', { duration: 3000 });
      return;
    }

    this.sellerService.updateOrderStatus(order.id, 'SHIPPED').subscribe({
      next: () => {
        order.status = 'SHIPPED';
        this.loadStats();
        this.snackBar.open('Order marked as shipped', 'Close', { duration: 3000 });
      },
      error: () => {
        this.snackBar.open('Failed to update order status', 'Close', { duration: 3000 });
      }
    });
  }

  getStatusClass(status: OrderStatus): string {
    switch (status) {
      case 'PLACED': return 'placed';
      case 'CONFIRMED': return 'confirmed';
      case 'SHIPPED': return 'shipped';
      case 'DELIVERED': return 'delivered';
      case 'CANCELLED': return 'cancelled';
      default: return '';
    }
  }

  getStatusLabel(status: OrderStatus): string {
    switch (status) {
      case 'PLACED': return 'New Order';
      case 'CONFIRMED': return 'Confirmed';
      case 'SHIPPED': return 'Shipped';
      case 'DELIVERED': return 'Delivered';
      case 'CANCELLED': return 'Cancelled';
      case 'RETURN_REQUESTED': return 'Return Requested';
      case 'REFUNDED': return 'Refunded';
      default: return status;
    }
  }

  viewOrder(orderId: number | undefined): void {
    if (orderId != null) {
      this.router.navigate(['/orders', orderId]);
    }
  }
}
