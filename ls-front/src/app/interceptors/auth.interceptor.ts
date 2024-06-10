import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  // méthode exécutée à chaque requête sortante
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // on vérifie si un token est disponible
    const token = this.authService.getToken();
    if (token) {
      // si oui, on l'ajoute à l'en-tête de la requête http
      const cloned = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`),
      });
      // puis l'intercepteur envoie la requête modifiée au gestionnaire de requêtes http
      return next.handle(cloned);
    } else {
      // si pas de token, la requête est trnasmise sans modification
      return next.handle(req);
    }
  }
}
