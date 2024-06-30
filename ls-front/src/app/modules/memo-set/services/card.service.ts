import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Card } from '../models/card';
import { AuthService } from '../../../services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class CardService {
  readonly API_URL = 'http://localhost:8080';

  constructor(
    private httpClient: HttpClient,
    private authService: AuthService
  ) {}

  public getCardsByMemo(memoId: number): Observable<Card[]> {
    const params = new HttpParams().set('memoId', memoId.toString());
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        Authorization: `Bearer ${this.authService.getToken()}`,
      }),
      params: params,
    };
    return this.httpClient.get<Card[]>(
      `${this.API_URL}/memoCarte/carte/${memoId}`,
      httpOptions
    );
  }

  public addCard(card: Card, memoId: number): Observable<Card> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        Authorization: `Bearer ${this.authService.getToken()}`,
      }),
      params: new HttpParams().set('memoId', memoId.toString()),
    };
    return this.httpClient.post<Card>(
      `${this.API_URL}/memoCarte/carte/creation`,
      card,
      httpOptions
    );
  }
}
