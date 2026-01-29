import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd, ActivatedRoute, RouterLink } from '@angular/router';
import { filter, distinctUntilChanged } from 'rxjs/operators';
import { MatIconModule } from '@angular/material/icon';

interface Breadcrumb {
  label: string;
  url: string;
}

@Component({
  selector: 'app-breadcrumb',
  standalone: true,
  imports: [CommonModule, RouterLink, MatIconModule],
  templateUrl: './breadcrumb.component.html',
  styleUrl: './breadcrumb.component.css'
})
export class BreadcrumbComponent implements OnInit {
  breadcrumbs: Breadcrumb[] = [];

  private routeLabels: { [key: string]: string } = {
    'products': 'Products',
    'cart': 'Cart',
    'wishlist': 'Wishlist',
    'orders': 'Orders',
    'checkout': 'Checkout',
    'profile': 'Profile',
    'compare': 'Compare Products',
    'login': 'Login',
    'register': 'Register',
    'seller': 'Seller',
    'dashboard': 'Dashboard',
    'admin': 'Admin',
    'reviews': 'Reviews'
  };

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd),
        distinctUntilChanged()
      )
      .subscribe(() => {
        this.breadcrumbs = this.buildBreadcrumbs(this.activatedRoute.root);
      });

    // Initial breadcrumb
    this.breadcrumbs = this.buildBreadcrumbs(this.activatedRoute.root);
  }

  private buildBreadcrumbs(
    route: ActivatedRoute,
    url: string = '',
    breadcrumbs: Breadcrumb[] = []
  ): Breadcrumb[] {
    // Add Home as first breadcrumb
    if (breadcrumbs.length === 0) {
      breadcrumbs.push({ label: 'Home', url: '/products' });
    }

    const children: ActivatedRoute[] = route.children;

    if (children.length === 0) {
      return breadcrumbs;
    }

    for (const child of children) {
      const routeURL: string = child.snapshot.url.map(segment => segment.path).join('/');
      if (routeURL !== '') {
        url += `/${routeURL}`;

        // Get the label for this route segment
        const label = this.getRouteLabel(routeURL, child);

        // Don't add duplicate or ID segments
        if (label && !this.isNumeric(routeURL)) {
          const lastBreadcrumb = breadcrumbs[breadcrumbs.length - 1];
          if (!lastBreadcrumb || lastBreadcrumb.url !== url) {
            breadcrumbs.push({ label, url });
          }
        }
      }

      return this.buildBreadcrumbs(child, url, breadcrumbs);
    }

    return breadcrumbs;
  }

  private getRouteLabel(routeURL: string, route: ActivatedRoute): string {
    // Check if route has custom data label
    if (route.snapshot.data['breadcrumb']) {
      return route.snapshot.data['breadcrumb'];
    }

    // Check our predefined labels
    const segments = routeURL.split('/');
    for (const segment of segments) {
      if (this.routeLabels[segment]) {
        return this.routeLabels[segment];
      }
    }

    // Capitalize first letter as fallback
    return routeURL.charAt(0).toUpperCase() + routeURL.slice(1);
  }

  private isNumeric(str: string): boolean {
    return !isNaN(Number(str));
  }
}
