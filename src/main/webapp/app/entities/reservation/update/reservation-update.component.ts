import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDiningTable } from 'app/entities/dining-table/dining-table.model';
import { DiningTableService } from 'app/entities/dining-table/service/dining-table.service';
import { IReservation } from '../reservation.model';
import { ReservationService } from '../service/reservation.service';
import { ReservationFormService, ReservationFormGroup } from './reservation-form.service';

@Component({
  standalone: true,
  selector: 'jhi-reservation-update',
  templateUrl: './reservation-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ReservationUpdateComponent implements OnInit {
  isSaving = false;
  reservation: IReservation | null = null;

  diningTablesSharedCollection: IDiningTable[] = [];

  editForm: ReservationFormGroup = this.reservationFormService.createReservationFormGroup();

  constructor(
    protected reservationService: ReservationService,
    protected reservationFormService: ReservationFormService,
    protected diningTableService: DiningTableService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareDiningTable = (o1: IDiningTable | null, o2: IDiningTable | null): boolean => this.diningTableService.compareDiningTable(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reservation }) => {
      this.reservation = reservation;
      if (reservation) {
        this.updateForm(reservation);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reservation = this.reservationFormService.getReservation(this.editForm);
    if (reservation.id !== null) {
      this.subscribeToSaveResponse(this.reservationService.update(reservation));
    } else {
      this.subscribeToSaveResponse(this.reservationService.create(reservation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReservation>>): void {
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

  protected updateForm(reservation: IReservation): void {
    this.reservation = reservation;
    this.reservationFormService.resetForm(this.editForm, reservation);

    this.diningTablesSharedCollection = this.diningTableService.addDiningTableToCollectionIfMissing<IDiningTable>(
      this.diningTablesSharedCollection,
      ...(reservation.diningTables ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.diningTableService
      .query()
      .pipe(map((res: HttpResponse<IDiningTable[]>) => res.body ?? []))
      .pipe(
        map((diningTables: IDiningTable[]) =>
          this.diningTableService.addDiningTableToCollectionIfMissing<IDiningTable>(
            diningTables,
            ...(this.reservation?.diningTables ?? []),
          ),
        ),
      )
      .subscribe((diningTables: IDiningTable[]) => (this.diningTablesSharedCollection = diningTables));
  }
}
