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
    { value: 'PLACED', label: 'Placed' },
    { value: 'CONFIRMED', label: 'Confirmed' },
    { value: 'SHIPPED', label: 'Shipped' },
    { value: 'DELIVERED', label: 'Delivered' },
    { value: 'CANCELLED', label: 'Cancelled' },
    { value: 'RETURN_REQUESTED', label: 'Return Requested' },
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
      next: (response: any) => {
        // Ensure orders is always an array
        this.orders = response?.content || [];
        this.totalElements = response?.totalElements || 0;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Failed to load orders:', error);
        this.orders = []; // Ensure orders is an array even on error
        this.totalElements = 0;
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
      case 'PLACED': return 'warn';
      case 'CONFIRMED':
      case 'SHIPPED': return 'primary';
      case 'DELIVERED': return 'accent';
      case 'CANCELLED':
      case 'RETURN_REQUESTED':
      case 'RETURN_APPROVED':
      case 'RETURN_REJECTED':
      case 'REFUNDED': return 'warn';
      default: return '';
    }
  }

  getStatusIcon(status: OrderStatus): string {
    switch (status) {
      case 'PLACED': return 'schedule';
      case 'CONFIRMED': return 'check_circle';
      case 'SHIPPED': return 'local_shipping';
      case 'DELIVERED': return 'done_all';
      case 'CANCELLED': return 'cancel';
      case 'RETURN_REQUESTED': return 'assignment_return';
      case 'RETURN_APPROVED': return 'check';
      case 'RETURN_REJECTED': return 'block';
      case 'REFUNDED': return 'replay';
      default: return 'info';
    }
  }
}
