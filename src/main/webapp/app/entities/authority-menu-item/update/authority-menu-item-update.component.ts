import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AuthorityMenuItemService } from '../service/authority-menu-item.service';
import { IAuthorityMenuItem } from '../authority-menu-item.model';
import { AuthorityMenuItemFormService, AuthorityMenuItemFormGroup } from './authority-menu-item-form.service';
import { IMenuItem } from '../menu-item.model';
import { IAuthority } from '../authority.model';

@Component({
  standalone: true,
  selector: 'jhi-authority-menu-item-update',
  templateUrl: './authority-menu-item-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AuthorityMenuItemUpdateComponent implements OnInit {
  isSaving = false;
  authorityMenuItem: IAuthorityMenuItem | null = null;

  menuItemsSharedCollection: IMenuItem[] = [];
  authoritiesSharedCollection: IAuthority[] = [];

  editForm: AuthorityMenuItemFormGroup = this.authorityMenuItemFormService.createAuthorityMenuItemFormGroup();

  constructor(
    protected authorityMenuItemService: AuthorityMenuItemService,
    protected authorityMenuItemFormService: AuthorityMenuItemFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ authorityMenuItem }) => {
      this.authorityMenuItem = authorityMenuItem;
      if (authorityMenuItem) {
        this.updateForm(authorityMenuItem);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const authorityMenuItem = this.authorityMenuItemFormService.getAuthorityMenuItem(this.editForm);
    if (authorityMenuItem.id !== null) {
      this.subscribeToSaveResponse(this.authorityMenuItemService.update(authorityMenuItem));
    } else {
      this.subscribeToSaveResponse(this.authorityMenuItemService.create(authorityMenuItem));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuthorityMenuItem>>): void {
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

  protected updateForm(authorityMenuItem: IAuthorityMenuItem): void {
    this.authorityMenuItem = authorityMenuItem;
    this.authorityMenuItemFormService.resetForm(this.editForm, authorityMenuItem);
  }
}
