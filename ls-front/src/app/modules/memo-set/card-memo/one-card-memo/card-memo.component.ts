import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

import { CardService } from '../../services/card.service';
import { Card } from '../../models/card';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-card-memo',
  templateUrl: './card-memo.component.html',
  styleUrl: './card-memo.component.scss',
  providers: [CardService],
})
export class CardMemoComponent implements OnInit {
  @ViewChild('addCardDialog') addCardDialog!: ElementRef<HTMLDialogElement>;

  cards: Card[] = [];
  addCardForm!: FormGroup;
  memoId: number = 0;

  constructor(
    private cardService: CardService,
    // permet d'accéder aux informations de la route/url
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {
    this.addCardForm = this.fb.group({
      recto: ['', Validators.required],
      verso: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      // on récupère l'id du mémo grâce à l'url
      this.memoId = +params['memoId'];
      this.getCards();
    });
  }

  getCards() {
    if (this.memoId) {
      this.cardService.getCardsByMemo(this.memoId).subscribe({
        next: (datas: Card[]) => {
          this.cards = datas;
          console.log(this.cards);
        },
        error: (error) => {
          console.error('Erreur lors de la récupération des cartes', error);
        },
        complete: () => {
          console.log('Récupération des cartes ok');
        },
      });
    }
  }

  addCard() {
    if (this.addCardForm.valid) {
      const card: Card = {
        recto: this.addCardForm.get('recto')?.value || '',
        verso: this.addCardForm.get('verso')?.value || '',
      };
      console.log(
        'Données du formulaire de création de carte avant envoi :',
        card
      );
      this.cardService.addCard(card, this.memoId).subscribe({
        next: (response) => {
          console.log('Réponse après ajout de carte :', response);
          this.getCards();
          this.addCardDialog.nativeElement.close();
          this.addCardForm.reset();
        },
        error: (error) => {
          console.error("Erreur lors de l'ajout de la carte", error);
        },
        complete: () => {
          console.log('Carte ajoutée !');
        },
      });
    }
  }

  closeDialog() {
    this.addCardDialog.nativeElement.close();
    this.addCardForm.reset();
  }
}
