import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMenuType } from 'app/entities/menu-type/menu-type.model';
import { MenuTypeService } from 'app/entities/menu-type/service/menu-type.service';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { ProductService } from '../service/product.service';
import { IProduct } from '../product.model';
import { ProductFormService, ProductFormGroup } from './product-form.service';

@Component({
  standalone: true,
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;
  product: IProduct | null = null;

  menuTypesSharedCollection: IMenuType[] = [];
  categoriesSharedCollection: ICategory[] = [];

  editForm: ProductFormGroup = this.productFormService.createProductFormGroup();

  constructor(
    protected productService: ProductService,
    protected productFormService: ProductFormService,
    protected menuTypeService: MenuTypeService,
    protected categoryService: CategoryService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareMenuType = (o1: IMenuType | null, o2: IMenuType | null): boolean => this.menuTypeService.compareMenuType(o1, o2);

  compareCategory = (o1: ICategory | null, o2: ICategory | null): boolean => this.categoryService.compareCategory(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.product = product;
      if (product) {
        this.updateForm(product);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.productFormService.getProduct(this.editForm);
    if (product.id !== null) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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

  protected updateForm(product: IProduct): void {
    this.product = product;
    this.productFormService.resetForm(this.editForm, product);

    this.menuTypesSharedCollection = this.menuTypeService.addMenuTypeToCollectionIfMissing<IMenuType>(
      this.menuTypesSharedCollection,
      product.menuType,
    );
    this.categoriesSharedCollection = this.categoryService.addCategoryToCollectionIfMissing<ICategory>(
      this.categoriesSharedCollection,
      product.category,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.menuTypeService
      .query()
      .pipe(map((res: HttpResponse<IMenuType[]>) => res.body ?? []))
      .pipe(
        map((menuTypes: IMenuType[]) =>
          this.menuTypeService.addMenuTypeToCollectionIfMissing<IMenuType>(menuTypes, this.product?.menuType),
        ),
      )
      .subscribe((menuTypes: IMenuType[]) => (this.menuTypesSharedCollection = menuTypes));

    this.categoryService
      .query()
      .pipe(map((res: HttpResponse<ICategory[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategory[]) =>
          this.categoryService.addCategoryToCollectionIfMissing<ICategory>(categories, this.product?.category),
        ),
      )
      .subscribe((categories: ICategory[]) => (this.categoriesSharedCollection = categories));
  }
}
