export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  originalPrice?: number;
  imageUrl: string;
  images?: string[];
  category: Category;
  brand: string;
  stock: number;
  rating: number;
  reviewCount: number;
  sellerId: number;
  sellerName?: string;
  premiumEarlyAccess: boolean;
  earlyAccessDate?: Date;
  createdAt?: Date;
}

export interface Category {
  id: number;
  name: string;
  description?: string;
}

export interface ProductFilter {
  categoryId?: number;
  minPrice?: number;
  maxPrice?: number;
  brand?: string;
  search?: string;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}