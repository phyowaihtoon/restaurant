import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProduct, NewProduct } from '../product.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProduct for edit and NewProductFormGroupInput for create.
 */
type ProductFormGroupInput = IProduct | PartialWithRequiredKeyOf<NewProduct>;

type ProductFormDefaults = Pick<NewProduct, 'id'>;

type ProductFormGroupContent = {
  id: FormControl<IProduct['id'] | NewProduct['id']>;
  productCode: FormControl<IProduct['productCode']>;
  productName: FormControl<IProduct['productName']>;
  imageURL: FormControl<IProduct['imageURL']>;
  description: FormControl<IProduct['description']>;
  status: FormControl<IProduct['status']>;
  menuType: FormControl<IProduct['menuType']>;
  category: FormControl<IProduct['category']>;
};

export type ProductFormGroup = FormGroup<ProductFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductFormService {
  createProductFormGroup(product: ProductFormGroupInput = { id: null }): ProductFormGroup {
    const productRawValue = {
      ...this.getFormDefaults(),
      ...product,
    };
    return new FormGroup<ProductFormGroupContent>({
      id: new FormControl(
        { value: productRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      productCode: new FormControl(productRawValue.productCode, {
        validators: [Validators.required, Validators.maxLength(20)],
      }),
      productName: new FormControl(productRawValue.productName, {
        validators: [Validators.required, Validators.maxLength(255)],
      }),
      imageURL: new FormControl(productRawValue.imageURL),
      description: new FormControl(productRawValue.description),
      status: new FormControl(productRawValue.status, {
        validators: [Validators.required],
      }),
      menuType: new FormControl(productRawValue.menuType),
      category: new FormControl(productRawValue.category),
    });
  }

  getProduct(form: ProductFormGroup): IProduct | NewProduct {
    return form.getRawValue() as IProduct | NewProduct;
  }

  resetForm(form: ProductFormGroup, product: ProductFormGroupInput): void {
    const productRawValue = { ...this.getFormDefaults(), ...product };
    form.reset(
      {
        ...productRawValue,
        id: { value: productRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProductFormDefaults {
    return {
      id: null,
    };
  }
}
