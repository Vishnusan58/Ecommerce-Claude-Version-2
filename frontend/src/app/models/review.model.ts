export interface Review {
  id: number;
  productId: number;
  userId: number;
  userName: string;
  rating: number;
  comment: string;
  createdAt: Date;
}

export interface CreateReviewRequest {
  rating: number;
  comment: string;
}