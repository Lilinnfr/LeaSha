import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

import { MemoCardService } from '../../services/card-memo.service';
import { CardMemo } from '../../models/card-memo';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Category } from '../../enums/category.enum';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-all-card-memos',
  templateUrl: './all-card-memos.component.html',
  styleUrl: './all-card-memos.component.scss',
  providers: [MemoCardService],
})
export class AllCardMemosComponent implements OnInit {
  @ViewChild('addMemoDialog') addMemoDialog!: ElementRef<HTMLDialogElement>;

  cardMemos: CardMemo[] = [];
  addMemoForm!: FormGroup;
  categories: { id: number; libelle: Category }[] = [];

  constructor(
    private memoCardService: MemoCardService,
    private categoryService: CategoryService,
    private fb: FormBuilder
  ) {
    this.addMemoForm = this.fb.group({
      titre: ['', Validators.required],
      categorie: ['', Validators.required],
      description: [''],
    });
  }

  ngOnInit(): void {
    this.getMemos();
    this.categories = this.categoryService.getCategories();
  }

  getMemos() {
    this.memoCardService.getAllCardMemos().subscribe({
      next: (response: CardMemo[]) => {
        this.cardMemos = response;
        console.log('Mémos : ', this.cardMemos);
      },
      error: (error) => {
        console.error('Erreur lors de la récupération des mémos', error);
      },
      complete: () => {
        console.log('Récupération des mémos ok');
      },
    });
  }

  addMemo() {
    if (this.addMemoForm.valid) {
      const memo: CardMemo = {
        titre: this.addMemoForm.get('titre')?.value || '',
        categorie: this.addMemoForm.get('categorie')?.value || '',
        description: this.addMemoForm.get('description')?.value || '',
      };
      console.log('Données du formulaire avant envoi :', memo);
      this.memoCardService.addMemo(memo).subscribe({
        next: (response) => {
          console.log('Réponse après ajout :', response);
          this.getMemos();
          this.addMemoDialog.nativeElement.close();
          this.addMemoForm.reset();
        },
        error: (error) => {
          console.error("Erreur lors de l'ajout du mémo", error);
        },
        complete: () => {
          console.log('Mémo ajouté !');
        },
      });
    }
  }

  closeDialog() {
    this.addMemoDialog.nativeElement.close();
    this.addMemoForm.reset();
  }
}
