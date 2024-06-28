import { Category } from '../enums/category.enum';
import { Card } from './card';

export class CardMemo {
  id?: number;
  titre?: string;
  description?: string;
  dateCreation?: Date;
  dateModif?: Date;
  categorie?: Category;
  carte?: Card[];
}
