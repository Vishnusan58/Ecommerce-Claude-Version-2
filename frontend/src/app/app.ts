import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { BreadcrumbComponent } from './components/breadcrumb/breadcrumb.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, BreadcrumbComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  title = 'E-Commerce Store';
}
