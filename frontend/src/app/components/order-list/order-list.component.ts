import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatSelectModule } from '@angular/material/select';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { FormsModule } from '@angular/forms';
import { OrderService } from '../../services/order.service';
import { AuthService } from '../../services/auth.service';
import { Order, OrderStatus } from '../../models/order.model';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatChipsModule,
    MatSelectModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatSnackBarModule
  ],
  templateUrl: './order-list.component.html',
  styleUrl: './order-list.component.css'
})
export class OrderListComponent implements OnInit {
  orders: Order[] = [];
  isLoading = true;
  selectedStatus: OrderStatus | '' = '';

  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;

  statusOptions: { value: OrderStatus | ''; label: string }[] = [
    { value: '', label: 'All Orders' },
    { value: 'PENDING', label: 'Pending' },
    { value: 'CONFIRMED', label: 'Confirmed' },
    { value: 'PROCESSING', label: 'Processing' },
    { value: 'SHIPPED', label: 'Shipped' },
    { value: 'DELIVERED', label: 'Delivered' },
    { value: 'CANCELLED', label: 'Cancelled' },
    { value: 'REFUNDED', label: 'Refunded' }
  ];

  constructor(
    private orderService: OrderService,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login'], { queryParams: { returnUrl: '/orders' } });
      return;
    }
    this.loadOrders();
  }

  loadOrders(): void {
    this.isLoading = true;
    const status = this.selectedStatus || undefined;

    this.orderService.getOrders(this.pageIndex, this.pageSize, status).subscribe({
      next: (response) => {
        this.orders = response.content;
        this.totalElements = response.totalElements;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.snackBar.open('Failed to load orders', 'Close', { duration: 3000 });
      }
    });
  }

  onStatusChange(): void {
    this.pageIndex = 0;
    this.loadOrders();
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadOrders();
  }

  viewOrder(orderId: number): void {
    this.router.navigate(['/orders', orderId]);
  }

  getStatusColor(status: OrderStatus): string {
    switch (status) {
      case 'PENDING': return 'warn';
      case 'CONFIRMED':
      case 'PROCESSING':
      case 'SHIPPED': return 'primary';
      case 'DELIVERED': return 'accent';
      case 'CANCELLED':
      case 'REFUNDED': return 'warn';
      default: return '';
    }
  }

  getStatusIcon(status: OrderStatus): string {
    switch (status) {
      case 'PENDING': return 'schedule';
      case 'CONFIRMED': return 'check_circle';
      case 'PROCESSING': return 'inventory_2';
      case 'SHIPPED': return 'local_shipping';
      case 'DELIVERED': return 'done_all';
      case 'CANCELLED': return 'cancel';
      case 'REFUNDED': return 'replay';
      default: return 'info';
    }
  }
}
