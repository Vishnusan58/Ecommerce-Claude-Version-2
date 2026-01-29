import { Product } from './product.model';

export interface Order {
  id: number;
  orderNumber: string;
  userId: number;
  items: OrderItem[];
  status: OrderStatus;
  subtotal: number;
  tax: number;
  deliveryFee: number;
  discount: number;
  total: number;
  shippingAddress: Address;
  paymentMethod: PaymentMethod;
  paymentStatus: PaymentStatus;
  isPremiumOrder: boolean;
  createdAt: Date;
  updatedAt?: Date;
  itemCount?: number; // Used for seller dashboard
  orderItemId?: number; // Used for seller dashboard
}

export interface OrderItem {
  id: number;
  product: Product;
  quantity: number;
  price: number;
  subtotal: number;
}

export interface Address {
  fullName: string;
  phone: string;
  street: string;
  city: string;
  state: string;
  zipCode: string;
  country: string;
}

export type OrderStatus = 'PLACED' | 'CONFIRMED' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED' | 'RETURN_REQUESTED' | 'RETURN_APPROVED' | 'RETURN_REJECTED' | 'REFUNDED';

export type PaymentMethod = 'UPI' | 'CARD' | 'COD';

export type PaymentStatus = 'PENDING' | 'COMPLETED' | 'FAILED' | 'REFUNDED';

export interface CreateOrderRequest {
  shippingAddress: Address;
  paymentMethod: PaymentMethod;
  paymentDetails?: PaymentDetails;
  couponCode?: string;
}

export interface PaymentDetails {
  upiId?: string;
  cardNumber?: string;
  cardExpiry?: string;
  cardCvv?: string;
  cardHolder?: string;
}