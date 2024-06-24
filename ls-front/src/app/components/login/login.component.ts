import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  @ViewChild('addCodeDialog') addCodeDialog!: ElementRef<HTMLDialogElement>;
  @ViewChild('registrationSuccessModal')
  registrationSuccessModal!: ElementRef<HTMLDialogElement>;

  registerForm!: FormGroup;
  codeForm!: FormGroup;
  loginForm!: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
    });

    this.codeForm = this.fb.group({
      code: ['', Validators.required],
    });

    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8)]],
    });
  }

  onRegister(): void {
    if (this.registerForm.valid) {
      this.authService.register(this.registerForm.value).subscribe({
        next: (response) => {
          console.log('Réponse inscription: ', response);
          this.addCodeDialog.nativeElement.showModal();
        },
        error: (error) => {
          console.error(error);
          this.errorMessage = "Une erreur est survenue lors de l'inscription";
        },
      });
    }
  }

  onSubmitCode() {
    if (this.codeForm.valid) {
      this.authService.submitCode(this.codeForm.value).subscribe({
        next: (response) => {
          this.addCodeDialog.nativeElement.close();
          setTimeout(() => {
            this.registrationSuccessModal.nativeElement.showModal();
          }, 0);
          console.log('inscription ok');
        },
      });
    }
  }

  onCloseSubmitCodeModal() {
    this.registrationSuccessModal.nativeElement.close();
    //this.router.navigate(['/utilisateur/connexion']);;
  }

  onLogin(): void {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      this.authService.login(username, password).subscribe({
        next: (response) => {
          this.router.navigate(['/']);
        },
        error: (error) => {
          this.errorMessage = "Nom d'utilisateur ou mot de passe incorrect";
        },
      });
    }
  }
}
