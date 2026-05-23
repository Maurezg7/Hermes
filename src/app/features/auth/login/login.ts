import { Component, inject } from '@angular/core';
import { AuthUser } from '../../models/auth-user.model';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  user: AuthUser = new AuthUser();
  private authService = inject(AuthService);
  private router = inject(Router);

  isLocked = true;
  typeLocked = 'password';

  miFuncion(event: Event) {
    event.preventDefault();
    this.isLocked = !this.isLocked;
    this.typeLocked = this.isLocked ? 'password' : 'text';
  }

  onSumbit() {
    this.loginUser();
  }

  private loginUser() {
    this.authService.loginRequest(this.user).subscribe({
      next: () => {
        this.authService.setSession(this.user.getDataUser());
        this.router.navigate(['/app']);
      },
    });
  }

  private irApp() {
    this.router.navigate(['/app'], {
      state: { authUser: this.user },
    });
  }
}
