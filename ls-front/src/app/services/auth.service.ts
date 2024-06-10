import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  readonly API_URL = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) {}

  register(user: any): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
      }),
    };
    return this.httpClient.post<any>(
      `${this.API_URL}/utilisateur/inscription`,
      user,
      httpOptions
    );
  }

  submitCode(code: string): Observable<void> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
      }),
    };
    return this.httpClient.post<void>(
      `${this.API_URL}/utilisateur/activation`,
      code,
      httpOptions
    );
  }

  login(username: string, password: string): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
      }),
    };
    return this.httpClient
      .post<any>(
        `${this.API_URL}/utilisateur/connexion`,
        { username, password },
        httpOptions
      )
      .pipe(
        tap((response) => {
          // On stocke le token dans le stockage local
          localStorage.setItem('token', response.token);
        })
      );
  }

  // Déconnecte l'utilisateur
  logout(): void {
    // On déconnecte l'utilisateur en le supprimant du stockage local
    localStorage.removeItem('currentUser');
  }

  // Récupère le token stocké dans le stockage local
  getToken(): string | null {
    // on récupère l'utilisateur actuel depuis le stockage local
    const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
    // on retourne le token de l'utilisateur ou null si pas de token
    return currentUser.token || null;
  }
}
