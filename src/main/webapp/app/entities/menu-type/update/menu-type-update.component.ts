import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMenuType } from '../menu-type.model';
import { MenuTypeService } from '../service/menu-type.service';
import { MenuTypeFormService, MenuTypeFormGroup } from './menu-type-form.service';

@Component({
  standalone: true,
  selector: 'jhi-menu-type-update',
  templateUrl: './menu-type-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MenuTypeUpdateComponent implements OnInit {
  isSaving = false;
  menuType: IMenuType | null = null;

  editForm: MenuTypeFormGroup = this.menuTypeFormService.createMenuTypeFormGroup();

  constructor(
    protected menuTypeService: MenuTypeService,
    protected menuTypeFormService: MenuTypeFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ menuType }) => {
      this.menuType = menuType;
      if (menuType) {
        this.updateForm(menuType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const menuType = this.menuTypeFormService.getMenuType(this.editForm);
    if (menuType.id !== null) {
      this.subscribeToSaveResponse(this.menuTypeService.update(menuType));
    } else {
      this.subscribeToSaveResponse(this.menuTypeService.create(menuType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMenuType>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(menuType: IMenuType): void {
    this.menuType = menuType;
    this.menuTypeFormService.resetForm(this.editForm, menuType);
  }
}
