import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, catchError, tap, throwError } from 'rxjs';
import { environment } from '../../environments/environments';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  readonly API_URL = environment.apiUrl;
  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  constructor(private httpClient: HttpClient, private router: Router) {
    // On ititialise le constructeur avec l'utilisateur actuel, cela permet de maintenir sa session active
    const storedUser = localStorage.getItem('currentUser');
    if (storedUser) {
      try {
        const currentUser = JSON.parse(storedUser);
        this.currentUserSubject = new BehaviorSubject<any>(currentUser);
      } catch (error) {
        console.error(
          "Erreur lors de l'analyse de l'utilisateur actuel à partir du stockage local:",
          error
        );
        this.currentUserSubject = new BehaviorSubject<any>(null);
      }
    } else {
      this.currentUserSubject = new BehaviorSubject<any>(null);
    }
    this.currentUser = this.currentUserSubject.asObservable();
  }

  register(user: any): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
      }),
    };
    localStorage.removeItem('currentUser');
    localStorage.removeItem('token');
    return this.httpClient
      .post<any>(`${this.API_URL}/utilisateur/inscription`, user, httpOptions)
      .pipe(
        tap((response) => {
          console.log('Réponse API:', response);
          if (response && response.id && response.username) {
            // On stocke les infos de l'utilisateur dans localStorage
            localStorage.setItem('currentUser', JSON.stringify(response));
            console.log(
              'Utilisateur actuel stocké:',
              localStorage.getItem('currentUser')
            );
          } else {
            console.error('Données invalides:', response);
            // On nettoie localStorage si nécessaire
            localStorage.removeItem('currentUser');
            //this.updateUser(null); // On met à jour currentUserSubject avec null
            throw new Error('Données invalides');
          }
        }),
        catchError((error) => {
          console.error("Erreur durant l'inscription:", error);
          return throwError(() => new Error("Erreur durant l'inscription"));
        })
      );
  }

  submitCode(code: string): Observable<void> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
      }),
    };
    return this.httpClient
      .post<void>(`${this.API_URL}/utilisateur/activation`, code, httpOptions)
      .pipe(catchError((error) => throwError(() => new Error(error.message))));
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
          console.log('Réponse API:', response);
          // On vérifie si response.bearer et response.refresh sont définis
          if (response.bearer && response.refresh) {
            // On stocke les tokens dans le stockage local
            localStorage.setItem('token', response.bearer);
            localStorage.setItem('refreshToken', response.refresh);

            const user = {
              username: username,
              token: response.bearer,
              refreshToken: response.refresh,
            };

            // On stocke les infos de l'utilisateur
            localStorage.setItem('currentUser', JSON.stringify(user));
            // On met à jour les informations utilisateur dans AuthService
            this.updateUser(user);
            console.log(
              'Utilisateur actuel stocké:',
              localStorage.getItem('currentUser')
            );
          } else {
            console.error('Données invalides:', response);
            // On nettoie localStorage si nécessaire
            localStorage.removeItem('currentUser');
            localStorage.removeItem('token');
            localStorage.removeItem('refreshToken');
            // On met à jour currentUserSubject avec null
            this.updateUser(null);
            throw new Error('Données invalides');
          }
        }),
        catchError((error) => {
          console.error('Erreur durant la connexion:', error);
          // Gérer l'erreur ici (par exemple, afficher un message à l'utilisateur)
          return throwError(() => new Error('Erreur durant la connexion'));
        })
      );
  }

  // Déconnecte l'utilisateur
  logout(): void {
    // On déconnecte l'utilisateur en le supprimant du stockage local
    localStorage.removeItem('currentUser');
    localStorage.removeItem('token');
    localStorage.removeItem('refreshToken');
    this.updateUser(null);
    this.router.navigate(['/']);
  }

  // Récupère le token stocké dans le stockage local
  getToken(): string | null {
    const storedUser = localStorage.getItem('currentUser');
    if (storedUser) {
      try {
        const currentUser = JSON.parse(storedUser);
        return currentUser.token || null;
      } catch (error) {
        console.error(
          "Erreur lors de l'analyse de l'utilisateur actuel à partir du stockage local:",
          error
        );
        // Gestion de l'erreur : On nettoie localStorage ou autre traitement nécessaire
        localStorage.removeItem('currentUser');
        return null;
      }
    }
    return null;
  }

  // Récupère l'utilisateur actuel
  getCurrentUser(): Observable<any> {
    return this.currentUserSubject.asObservable();
  }

  // Méthode pour mettre à jour currentUserSubject avec les informations utilisateur
  public updateUser(user: any) {
    console.log('Utilisateur mis à jour:', user);
    this.currentUserSubject.next(user);
  }

  // Vérifie si un utilisateur est connecté en vérifiant la présence d'un token
  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
