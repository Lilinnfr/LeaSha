import { Component, OnInit, ViewChild } from '@angular/core';

import { MemoCardService } from '../../services/memo-card.service';
import { MemoCard } from '../../models/memo-card';
import { Card } from '../../models/card';

@Component({
  selector: 'app-all-card-memos',
  templateUrl: './all-card-memos.component.html',
  styleUrl: './all-card-memos.component.scss',
  providers: [MemoCardService],
})
export class AllCardMemosComponent implements OnInit {
  cardMemos: MemoCard[] = [];
  cards: Card[] = [];
  constructor(private memoCardService: MemoCardService) {}
  ngOnInit(): void {
    this.memoCardService.getMemoCard().subscribe((datas: MemoCard[]) => {
      this.cardMemos = datas;
      console.log(this.cardMemos);
    });
  }
}
