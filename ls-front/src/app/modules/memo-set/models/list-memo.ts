import { Category } from '../enums/category.enum';

export class ListMemo {
  id?: number;
  titre?: string;
  description?: string;
  liste?: string;
  dateCreation?: Date;
  dateModif?: Date;
  categorie?: Category;
}
