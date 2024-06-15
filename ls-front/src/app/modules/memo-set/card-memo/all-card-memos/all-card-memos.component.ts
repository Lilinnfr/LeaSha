import { Component, OnInit, ViewChild } from '@angular/core';

import { MemoCardService } from '../../services/card-memo.service';
import { CardMemo } from '../../models/card-memo';
import { Card } from '../../models/card';

@Component({
  selector: 'app-all-card-memos',
  templateUrl: './all-card-memos.component.html',
  styleUrl: './all-card-memos.component.scss',
  providers: [MemoCardService],
})
export class AllCardMemosComponent implements OnInit {
  cardMemos: CardMemo[] = [];
  cards: Card[] = [];

  constructor(private memoCardService: MemoCardService) {}

  ngOnInit(): void {
    this.getMemos();
  }

  getMemos() {
    this.memoCardService.getAllCardMemos().subscribe({
      next: (datas: CardMemo[]) => {
        this.cardMemos = datas;
        console.log(this.cardMemos);
      },
      error: (error) => {
        console.error('Erreur lors de la récupération des mémos', error);
      },
      complete: () => {
        console.log('Récupération des mémos ok');
      },
    });
  }
}
