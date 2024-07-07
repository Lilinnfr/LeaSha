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
  @ViewChild('confirmDeleteDialog')
  confirmDeleteDialog!: ElementRef<HTMLDialogElement>;
  @ViewChild('reviewDialog') reviewDialog!: ElementRef<HTMLDialogElement>;
  @ViewChild('endReviewDialog') endReviewDialog!: ElementRef<HTMLDialogElement>;

  cards: Card[] = [];
  reviewedCards: Set<number> = new Set();
  addCardForm!: FormGroup;
  memoId: number = 0;
  selectedCardId: number | null = null;
  cardIdToDelete: number | null = null;
  submitButtonText: string = 'Ajouter';

  randomCard: Card | null = null;
  showVerso: boolean = false;

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

  updateCard() {
    if (this.addCardForm.valid && this.selectedCardId !== null) {
      const card: Card = {
        recto: this.addCardForm.get('recto')?.value || '',
        verso: this.addCardForm.get('verso')?.value || '',
      };
      this.cardService.updateCard(this.selectedCardId!, card).subscribe({
        next: (response) => {
          console.log(
            'Réponse du backend après modification de carte :',
            response
          );
          this.getCards();
          this.addCardDialog.nativeElement.close();
          this.addCardForm.reset();
          this.selectedCardId = null;
        },
        error: (error) => {
          console.error('Erreur lors de la modification de la carte', error);
        },
        complete: () => {
          console.log('Carte modifiée avec succès');
        },
      });
    }
  }

  editCard(card: Card) {
    this.submitButtonText = 'Modifier';
    this.selectedCardId = card.id!;
    this.addCardForm.patchValue({
      recto: card.recto,
      verso: card.verso,
    });
    this.addCardDialog.nativeElement.show();
  }

  openDeleteConfirmDialog(cardId: number) {
    this.cardIdToDelete = cardId;
    this.confirmDeleteDialog.nativeElement.show();
  }

  closeConfirmDeleteDialog() {
    this.cardIdToDelete = null;
    this.confirmDeleteDialog.nativeElement.close();
  }

  confirmDeleteCard() {
    if (this.cardIdToDelete !== null) {
      this.cardService.deleteCard(this.cardIdToDelete).subscribe({
        next: () => {
          console.log('Carte supprimée');
          this.getCards();
          this.closeConfirmDeleteDialog();
        },
        error: (error) => {
          console.error('Erreur lors de la suppression de la carte', error);
        },
        complete: () => {
          console.log('Suppression de la carte ok');
        },
      });
    }
  }

  closeDialog() {
    this.submitButtonText = 'Ajouter';
    this.addCardDialog.nativeElement.close();
    this.addCardForm.reset();
  }

  openReviewDialog() {
    this.reviewedCards.clear();
    this.nextCard();
    this.reviewDialog.nativeElement.show();
  }

  closeReviewDialog() {
    this.reviewDialog.nativeElement.close();
    this.randomCard = null;
    this.showVerso = false;
  }

  nextCard() {
    if (this.reviewedCards.size === this.cards.length) {
      this.endReviewDialog.nativeElement.showModal();
      return;
    }

    let randomCardFound = false;
    while (!randomCardFound) {
      const randomIndex = Math.floor(Math.random() * this.cards.length);
      if (!this.reviewedCards.has(randomIndex)) {
        this.reviewedCards.add(randomIndex);
        this.randomCard = this.cards[randomIndex];
        this.showVerso = false;
        randomCardFound = true;
      }
    }
  }

  flipCard() {
    this.showVerso = !this.showVerso;
  }

  resetReview() {
    this.reviewedCards.clear();
    this.closeReviewDialog();
    this.endReviewDialog.nativeElement.close();
  }
}
