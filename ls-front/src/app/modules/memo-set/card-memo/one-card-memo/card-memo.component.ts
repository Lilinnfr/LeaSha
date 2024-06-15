import { Component, OnInit } from '@angular/core';

import { CardService } from '../../services/card.service';
import { Card } from '../../models/card';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-card-memo',
  templateUrl: './card-memo.component.html',
  styleUrl: './card-memo.component.scss',
  providers: [CardService],
})
export class CardMemoComponent {
  cards: Card[] = [];
  memoId: number = 0;

  constructor(
    private cardService: CardService,
    private route: ActivatedRoute // permet d'accéder aux informations de la route/url
  ) {}
  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.memoId = +params['memoId']; // récupère le memoId de l'URL
      this.cardService
        .getCardsByMemo(this.memoId)
        .subscribe((datas: Card[]) => {
          this.cards = datas;
          console.log(this.cards);
        });
    });
  }
}
