import { Routes } from '@angular/router';
import { Home } from './features/pages/home/home';
import { Main } from './features/pages/main/main';
import { Login } from './features/auth/login/login';
import { Register } from './features/auth/register/register';
import { Verify } from './features/auth/verify/verify';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'app', component: Main },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'verify', component: Verify },
];
