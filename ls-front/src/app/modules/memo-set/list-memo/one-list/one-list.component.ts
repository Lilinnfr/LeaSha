import { Component, OnInit } from '@angular/core';
import { ListMemo } from '../../models/list-memo';
import { ActivatedRoute, Router } from '@angular/router';
import { ListMemoService } from '../../services/list-memo.service';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-one-list',
  templateUrl: './one-list.component.html',
  styleUrl: './one-list.component.scss',
})
export class OneListComponent implements OnInit {
  listMemo: ListMemo = { titre: '', description: '', liste: '' };
  listMemoId!: number;
  listMemoForm: FormGroup;
  successMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private listMemoService: ListMemoService,
    private fb: FormBuilder
  ) {
    this.listMemoForm = this.fb.group({
      titre: [''],
      description: [''],
      liste: [''],
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.listMemoId = +params['memoId'];
      if (this.listMemoId) {
        this.getListMemo();
      } else {
        console.error('Id de mémo invalide');
      }
    });
  }

  getListMemo(): void {
    this.listMemoService.getListMemoById(this.listMemoId).subscribe({
      next: (data: ListMemo) => {
        this.listMemo = data;
        this.listMemoForm.patchValue({
          titre: data.titre,
          description: data.description,
          liste: data.liste,
        });
      },
      error: (error) => {
        console.error('Erreur lors de la récupération de la liste', error);
      },
    });
  }

  saveList(): void {
    const updatedMemo = {
      ...this.listMemo,
      liste: this.listMemoForm.get('liste')?.value,
    };
    this.listMemoService.updateMemo(this.listMemoId, updatedMemo).subscribe({
      next: () => {
        console.log('Liste sauvegardée avec succès');
        this.successMessage = 'Liste sauvegardée avec succès';
        setTimeout(() => (this.successMessage = ''), 3000);
      },
      error: (error) => {
        console.error('Erreur lors de la sauvegarde de la liste', error);
      },
    });
  }

  cancel(): void {
    this.router.navigate(['/memoListe/Mes mémos listes']);
  }
}
