import { Product } from './product.model';

export interface CartItem {
  id: number;
  product: Product;
  quantity: number;
  subtotal: number;
}

export interface Cart {
  id: number;
  items: CartItem[];
  subtotal: number;
  tax: number;
  deliveryFee: number;
  discount: number;
  total: number;
  appliedCoupon?: string;
}

export interface AddToCartRequest {
  productId: number;
  quantity: number;
}