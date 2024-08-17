import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Card } from '../models/card';
import { AuthService } from '../../../services/auth.service';
import { environment } from '../../../../environments/environments';
import { CardMemo } from '../models/card-memo';

@Injectable({
  providedIn: 'root',
})
export class CardService {
  readonly API_URL = environment.apiUrl;

  constructor(
    private httpClient: HttpClient,
    private authService: AuthService
  ) {}

  public getMemo(memoId: number): Observable<CardMemo> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        Authorization: `Bearer ${this.authService.getToken()}`,
      }),
    };
    return this.httpClient.get<CardMemo>(
      `${this.API_URL}/memoCarte/${memoId}`,
      httpOptions
    );
  }

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

  public updateCard(id: number, card: Card): Observable<Card> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        Authorization: `Bearer ${this.authService.getToken()}`,
      }),
    };
    return this.httpClient.put<Card>(
      `${this.API_URL}/memoCarte/carte/modification/${id}`,
      card,
      httpOptions
    );
  }

  public deleteCard(id: number): Observable<void> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        Authorization: `Bearer ${this.authService.getToken()}`,
      }),
    };
    return this.httpClient.delete<void>(
      `${this.API_URL}/memoCarte/carte/suppression/${id}`,
      httpOptions
    );
  }
}
