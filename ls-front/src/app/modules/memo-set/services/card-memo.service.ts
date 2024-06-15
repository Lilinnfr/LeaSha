import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Card } from '../models/card';
import { CardMemo } from '../models/card-memo';
import { AuthService } from '../../../services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class MemoCardService {
  readonly API_URL = 'http://localhost:8080';

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
}
