import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

import { CardMemoService } from '../../services/card-memo.service';
import { CardMemo } from '../../models/card-memo';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Category } from '../../enums/category.enum';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-all-card-memos',
  templateUrl: './all-card-memos.component.html',
  styleUrl: './all-card-memos.component.scss',
  providers: [CardMemoService],
})
export class AllCardMemosComponent implements OnInit {
  @ViewChild('addMemoDialog') addMemoDialog!: ElementRef<HTMLDialogElement>;
  @ViewChild('confirmDeleteDialog')
  confirmDeleteDialog!: ElementRef<HTMLDialogElement>;

  cardMemos: CardMemo[] = [];
  addMemoForm!: FormGroup;
  categories: { id: number; libelle: Category }[] = [];
  selectedMemoId: number | null = null;
  memoIdToDelete: number | null = null;
  submitButtonText: string = 'Ajouter';

  constructor(
    private cardMemoService: CardMemoService,
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
    this.cardMemoService.getAllCardMemos().subscribe({
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
      this.cardMemoService.addMemo(memo).subscribe({
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

  updateMemo() {
    if (this.addMemoForm.valid && this.selectedMemoId !== null) {
      const memo: CardMemo = {
        titre: this.addMemoForm.get('titre')?.value || '',
        categorie: this.addMemoForm.get('categorie')?.value || '',
        description: this.addMemoForm.get('description')?.value || '',
      };
      this.cardMemoService.updateMemo(this.selectedMemoId!, memo).subscribe({
        next: (response) => {
          console.log(
            'Réponse du backend après modification de mémo :',
            response
          );
          this.getMemos();
          this.addMemoDialog.nativeElement.close();
          this.addMemoForm.reset();
          this.selectedMemoId = null;
        },
        error: (error) => {
          console.error('Erreur lors de la modification du mémo', error);
        },
        complete: () => {
          console.log('Mémo modifié avec succès');
        },
      });
    }
  }

  editMemo(memo: CardMemo) {
    this.submitButtonText = 'Modifier';
    this.selectedMemoId = memo.id!;
    this.addMemoForm.patchValue({
      titre: memo.titre,
      categorie: memo.categorie,
      description: memo.description,
    });
    this.addMemoDialog.nativeElement.show();
  }

  openDeleteConfirmDialog(memoId: number) {
    this.memoIdToDelete = memoId;
    this.confirmDeleteDialog.nativeElement.show();
  }

  closeConfirmDeleteDialog() {
    this.memoIdToDelete = null;
    this.confirmDeleteDialog.nativeElement.close();
  }

  confirmDeleteMemo() {
    if (this.memoIdToDelete !== null) {
      this.cardMemoService.deleteMemo(this.memoIdToDelete).subscribe({
        next: () => {
          console.log('Mémo supprimé');
          this.getMemos();
          this.closeConfirmDeleteDialog();
        },
        error: (error) => {
          console.error('Erreur lors de la suppression du mémo', error);
        },
        complete: () => {
          console.log('Suppression du mémo ok');
        },
      });
    }
  }

  closeDialog() {
    this.submitButtonText = 'Ajouter';
    this.addMemoDialog.nativeElement.close();
    this.addMemoForm.reset();
  }
}
