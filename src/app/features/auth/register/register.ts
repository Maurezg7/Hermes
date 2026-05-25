import { Component, inject } from '@angular/core';
import { User } from '../../models/user.model';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthUser } from '../../models/auth-user.model';
import { Server } from '../../models/server.model';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {
  loading: boolean = true;
  private authService = inject(AuthService);
  private router = inject(Router);
  user: User = new User();
  authUser: AuthUser = new AuthUser();
  server: Server = new Server();
  confirmPassword: string = '';
  isLocked = true;
  typeLocked = 'password';


  onSumbit() {
    if (this.loading) return;
    this.loading = false;
    if (this.user.password === this.confirmPassword) {
      this.registerUser();
    } else {
      alert('Las contraseñas no coinciden');
    }
  }

  miFuncion(event: Event) {
    event.preventDefault();
    this.isLocked = this.isLocked ? false : true;
    this.typeLocked = this.isLocked ? 'password' : 'text';
  }

  private registerUser() {
    this.loading = true;
    this.authService.postRegister(this.user).subscribe({
      next: (data) => {
        this.loading = false;
        this.irLogin();
      },
      error: (error: any) => {
        this.loading = false;
        alert('Ocurrió un error al registrar: ' + (error.error?.message || error.message || error));
      },
    });
  }

  private irLogin() {
    this.authUser.setDataUser(this.user.username || this.user.email);
    this.authUser.setDataPassword(this.user.password);

    this.router.navigate(['/login']);
  }
}
