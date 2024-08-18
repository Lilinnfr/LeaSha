import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../../../services/auth.service';
import { environment } from '../../../../environments/environments';
import { ListMemo } from '../models/list-memo';

@Injectable({
  providedIn: 'root',
})
export class ListMemoService {
  readonly API_URL = environment.apiUrl;

  constructor(
    private httpClient: HttpClient,
    private authService: AuthService
  ) {}

  public getAllListMemos(): Observable<ListMemo[]> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        Authorization: `Bearer ${this.authService.getToken()}`,
      }),
    };
    return this.httpClient.get<ListMemo[]>(
      `${this.API_URL}/memoListe/Mes mémos listes`,
      httpOptions
    );
  }

  public getListMemoById(id: number): Observable<ListMemo> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        Authorization: `Bearer ${this.authService.getToken()}`,
      }),
    };
    return this.httpClient.get<ListMemo>(
      `${this.API_URL}/memoListe/${id}`,
      httpOptions
    );
  }

  public addMemo(memo: ListMemo): Observable<ListMemo> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        Authorization: `Bearer ${this.authService.getToken()}`,
      }),
    };
    return this.httpClient.post<ListMemo>(
      `${this.API_URL}/memoListe/creation`,
      memo,
      httpOptions
    );
  }

  public updateMemo(id: number, memo: ListMemo): Observable<ListMemo> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        Authorization: `Bearer ${this.authService.getToken()}`,
      }),
    };
    return this.httpClient.put<ListMemo>(
      `${this.API_URL}/memoListe/modification/${id}`,
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
      `${this.API_URL}/memoListe/suppression/${id}`,
      httpOptions
    );
  }
}
