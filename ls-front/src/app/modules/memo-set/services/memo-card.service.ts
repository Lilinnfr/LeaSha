import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Card } from '../models/card';
import { MemoCard } from '../models/memo-card';

@Injectable({
  providedIn: 'root',
})
export class MemoCardService {
  readonly API_URL = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) {}

  public getMemoCard(): Observable<MemoCard[]> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
      }),
    };
    return this.httpClient.get<MemoCard[]>(
      `${this.API_URL}/memoCarte/liste`,
      httpOptions
    );
  }
}
