import { Component, OnInit } from '@angular/core';

import { MemoCardService } from '../services/memo-card.service';
import { Card } from '../models/card';
import { MemoCard } from '../models/memo-card';

@Component({
  selector: 'app-card-memo',
  templateUrl: './card-memo.component.html',
  styleUrl: './card-memo.component.scss',
  providers: [MemoCardService],
})
export class CardMemoComponent implements OnInit {
  cardMemos: MemoCard[] = [];
  constructor(private memoCardService: MemoCardService) {}
  ngOnInit(): void {
    console.log('ok');
    this.memoCardService.getMemoCard().subscribe((datas: Card[]) => {
      this.cardMemos = datas;
    });
  }
}
