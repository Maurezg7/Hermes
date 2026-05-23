import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { AuthUser } from '../../models/auth-user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-verify',
  imports: [FormsModule],
  templateUrl: './verify.html',
  styleUrl: './verify.scss',
})
export class Verify {
  private authService = inject(AuthService);
  private router = inject(Router);

  code: string[] = ['', '', '', '', '', ''];
  authUser: AuthUser = new AuthUser();

  constructor() {
    const navigation = this.router.getCurrentNavigation();
    const state = navigation?.extras.state as { authUser: any };

    if (state && state.authUser) {
      this.authUser.setDataUser(state.authUser.dataUser);
      this.authUser.setDataPassword(state.authUser.dataPassword);
    }
  }

  ngOnInit() {
    if (!this.authUser.getDataUser()) {
      this.router.navigate(['/login']);
    }
  }

  onKeyUp(event: KeyboardEvent, index: number) {
    const target = event.target as HTMLInputElement;

    if (target.value && index < 5) {
      const nextInput = document.getElementById(`digit-${index + 1}`);
      nextInput?.focus();
    }

    if (event.key === 'Backspace' && index > 0 && !target.value) {
      const prevInput = document.getElementById(`digit-${index - 1}`);
      prevInput?.focus();
    }
  }

  onSubmit() {
    const fullCode = this.code.join('');

    if (fullCode.length < 6) {
      alert('Por favor completa los 6 dígitos');
      return;
    }

    this.authService.verifyUser(this.authUser, fullCode).subscribe({
      next: (res) => {
        this.router.navigate(['/app'], {
          state: { authUser: this.authUser },
        });
      },
      error: (err) => {
        alert('Código incorrecto o expirado');
        console.error(err);
      },
    });
  }
}
