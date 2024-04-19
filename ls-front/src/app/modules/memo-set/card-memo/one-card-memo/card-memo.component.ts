import { Component, OnInit } from '@angular/core';

import { MemoCardService } from '../../services/memo-card.service';
import { MemoCard } from '../../models/memo-card';
import { CardService } from '../../services/card.service';
import { Card } from '../../models/card';

@Component({
  selector: 'app-card-memo',
  templateUrl: './card-memo.component.html',
  styleUrl: './card-memo.component.scss',
  providers: [MemoCardService, CardService],
})
export class CardMemoComponent {}
