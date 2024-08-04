import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CardMemo } from '../models/card-memo';
import { AuthService } from '../../../services/auth.service';
import { environment } from '../../../../environments/environments';

@Injectable({
  providedIn: 'root',
})
export class CardMemoService {
  readonly API_URL = environment.apiUrl;

  constructor(
    private httpClient: HttpClient,
    private authService: AuthService
  ) {}

  public getAllCardMemos(): Observable<CardMemo[]> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        Authorization: `Bearer ${this.authService.getToken()}`,
      }),
    };
    return this.httpClient.get<CardMemo[]>(
      `${this.API_URL}/memoCarte/Mes mémos cartes`,
      httpOptions
    );
  }

  public addMemo(memo: CardMemo): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        Authorization: `Bearer ${this.authService.getToken()}`,
      }),
    };
    return this.httpClient.post<CardMemo[]>(
      `${this.API_URL}/memoCarte/creation`,
      memo,
      httpOptions
    );
  }

  public updateMemo(id: number, memo: CardMemo): Observable<CardMemo> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        Authorization: `Bearer ${this.authService.getToken()}`,
      }),
    };
    return this.httpClient.put<CardMemo>(
      `${this.API_URL}/memoCarte/modification/${id}`,
      memo,
      httpOptions
    );
  }

  public deleteMemo(id: number): Observable<void> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        Authorization: `Bearer ${this.authService.getToken()}`,
      }),
    };
    return this.httpClient.delete<void>(
      `${this.API_URL}/memoCarte/suppression/${id}`,
      httpOptions
    );
  }
}
