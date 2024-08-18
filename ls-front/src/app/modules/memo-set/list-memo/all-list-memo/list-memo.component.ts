import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ListMemo } from '../../models/list-memo';
import { ListMemoService } from '../../services/list-memo.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Category } from '../../enums/category.enum';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-list-memo',
  templateUrl: './list-memo.component.html',
  styleUrl: './list-memo.component.scss',
})
export class ListMemoComponent implements OnInit {
  @ViewChild('addMemoDialog') addMemoDialog!: ElementRef<HTMLDialogElement>;
  @ViewChild('confirmDeleteDialog')
  confirmDeleteDialog!: ElementRef<HTMLDialogElement>;

  listMemos: ListMemo[] = [];
  addMemoForm!: FormGroup;
  categories: { id: number; libelle: Category }[] = [];
  selectedMemoId: number | null = null;
  memoIdToDelete: number | null = null;
  submitButtonText: string = 'Ajouter';
  isDescriptionVisible: { [key: number]: boolean } = {};

  constructor(
    private listMemoService: ListMemoService,
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
    this.listMemoService.getAllListMemos().subscribe({
      next: (response: ListMemo[]) => {
        this.listMemos = response;
        console.log('Mémos : ', this.listMemos);
      },
      error: (error) => {
        console.error('Erreur lors de la récupération des mémos', error);
      },
      complete: () => {
        console.log('Récupération des mémos ok');
      },
    });
  }

  onSubmit() {
    if (this.addMemoForm.valid) {
      if (this.selectedMemoId === null) {
        this.addMemo();
      } else {
        this.updateMemo();
      }
    } else {
      this.addMemoForm.markAllAsTouched();
    }
  }

  addMemo() {
    if (this.addMemoForm.valid) {
      const memo: ListMemo = {
        titre: this.addMemoForm.get('titre')?.value || '',
        categorie: this.addMemoForm.get('categorie')?.value || '',
        description: this.addMemoForm.get('description')?.value || '',
      };
      console.log('Données du formulaire avant envoi :', memo);
      this.listMemoService.addMemo(memo).subscribe({
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
      const memo: ListMemo = {
        id: this.selectedMemoId,
        titre: this.addMemoForm.get('titre')?.value || '',
        categorie: this.addMemoForm.get('categorie')?.value || '',
        description: this.addMemoForm.get('description')?.value || '',
      };
      this.listMemoService.updateMemo(this.selectedMemoId!, memo).subscribe({
        next: (response) => {
          console.log(
            'Réponse du backend après modification de mémo :',
            response
          );
          this.getMemos();
          this.closeDialog();
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

  editMemo(memo: ListMemo) {
    this.selectedMemoId = memo.id!;
    this.submitButtonText = 'Modifier';
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
      this.listMemoService.deleteMemo(this.memoIdToDelete).subscribe({
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
    this.selectedMemoId = null;
    this.submitButtonText = 'Ajouter';
    this.addMemoForm.reset();
    this.addMemoDialog.nativeElement.close();
  }

  toggleDescription(memoId: number) {
    this.isDescriptionVisible[memoId] = !this.isDescriptionVisible[memoId];
  }
}
