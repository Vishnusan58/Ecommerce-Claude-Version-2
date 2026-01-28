export interface User {
  id: number;
  email: string;
  name: string;
  role: 'USER' | 'SELLER' | 'ADMIN';
  premiumStatus: boolean;
  premiumExpiry?: Date;
  createdAt?: Date;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
  role: 'USER' | 'SELLER';
}

export interface AuthResponse {
  token: string;
  user: User;
}