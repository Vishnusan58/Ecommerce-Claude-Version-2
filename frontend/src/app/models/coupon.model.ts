export interface Coupon {
  id: number;
  code: string;
  discountType: 'PERCENTAGE' | 'FIXED';
  discountValue: number;
  minOrderValue: number;
  maxDiscount?: number;
  validFrom: Date;
  validUntil: Date;
  usageLimit: number;
  usedCount: number;
  isActive: boolean;
}

export interface CreateCouponRequest {
  code: string;
  discountType: 'PERCENTAGE' | 'FIXED';
  discountValue: number;
  minOrderValue: number;
  maxDiscount?: number;
  validFrom: Date;
  validUntil: Date;
  usageLimit: number;
}
