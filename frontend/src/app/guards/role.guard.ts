import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const requiredRole = route.data['role'] as string;

  if (!authService.isLoggedIn()) {
    router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }

  if (authService.hasRole(requiredRole)) {
    return true;
  }

  // User doesn't have the required role
  router.navigate(['/products']);
  return false;
};
