import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDiningTable } from '../dining-table.model';
import { DiningTableService } from '../service/dining-table.service';
import { DiningTableFormService, DiningTableFormGroup } from './dining-table-form.service';
import { LoadSetupService } from 'app/entities/common/service/load-setup.service';
import { IPickListData } from 'app/entities/common/model/picklist.model';

@Component({
  standalone: true,
  selector: 'jhi-dining-table-update',
  templateUrl: './dining-table-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DiningTableUpdateComponent implements OnInit {
  isSaving = false;
  diningTable: IDiningTable | null = null;
  tableStatusList: IPickListData[] = [];

  editForm: DiningTableFormGroup = this.diningTableFormService.createDiningTableFormGroup();

  constructor(
    protected diningTableService: DiningTableService,
    protected diningTableFormService: DiningTableFormService,
    protected activatedRoute: ActivatedRoute,
    protected loadSetupService: LoadSetupService,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ diningTable }) => {
      this.diningTable = diningTable;
      if (diningTable) {
        this.updateForm(diningTable);
      }
    });

    this.loadSetupService.loadPicklist().subscribe(res => {
      if (res.body) {
        this.tableStatusList = res.body.tableStatusList;
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const diningTable = this.diningTableFormService.getDiningTable(this.editForm);
    if (diningTable.id !== null) {
      this.subscribeToSaveResponse(this.diningTableService.update(diningTable));
    } else {
      this.subscribeToSaveResponse(this.diningTableService.create(diningTable));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiningTable>>): void {
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

  protected updateForm(diningTable: IDiningTable): void {
    this.diningTable = diningTable;
    this.diningTableFormService.resetForm(this.editForm, diningTable);
  }
}
