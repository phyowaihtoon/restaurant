import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMenuType } from '../menu-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../menu-type.test-samples';

import { MenuTypeService } from './menu-type.service';

const requireRestSample: IMenuType = {
  ...sampleWithRequiredData,
};

describe('MenuType Service', () => {
  let service: MenuTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: IMenuType | IMenuType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MenuTypeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a MenuType', () => {
      const menuType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(menuType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MenuType', () => {
      const menuType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(menuType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MenuType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MenuType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MenuType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMenuTypeToCollectionIfMissing', () => {
      it('should add a MenuType to an empty array', () => {
        const menuType: IMenuType = sampleWithRequiredData;
        expectedResult = service.addMenuTypeToCollectionIfMissing([], menuType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(menuType);
      });

      it('should not add a MenuType to an array that contains it', () => {
        const menuType: IMenuType = sampleWithRequiredData;
        const menuTypeCollection: IMenuType[] = [
          {
            ...menuType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMenuTypeToCollectionIfMissing(menuTypeCollection, menuType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MenuType to an array that doesn't contain it", () => {
        const menuType: IMenuType = sampleWithRequiredData;
        const menuTypeCollection: IMenuType[] = [sampleWithPartialData];
        expectedResult = service.addMenuTypeToCollectionIfMissing(menuTypeCollection, menuType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(menuType);
      });

      it('should add only unique MenuType to an array', () => {
        const menuTypeArray: IMenuType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const menuTypeCollection: IMenuType[] = [sampleWithRequiredData];
        expectedResult = service.addMenuTypeToCollectionIfMissing(menuTypeCollection, ...menuTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const menuType: IMenuType = sampleWithRequiredData;
        const menuType2: IMenuType = sampleWithPartialData;
        expectedResult = service.addMenuTypeToCollectionIfMissing([], menuType, menuType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(menuType);
        expect(expectedResult).toContain(menuType2);
      });

      it('should accept null and undefined values', () => {
        const menuType: IMenuType = sampleWithRequiredData;
        expectedResult = service.addMenuTypeToCollectionIfMissing([], null, menuType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(menuType);
      });

      it('should return initial array if no MenuType is added', () => {
        const menuTypeCollection: IMenuType[] = [sampleWithRequiredData];
        expectedResult = service.addMenuTypeToCollectionIfMissing(menuTypeCollection, undefined, null);
        expect(expectedResult).toEqual(menuTypeCollection);
      });
    });

    describe('compareMenuType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMenuType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMenuType(entity1, entity2);
        const compareResult2 = service.compareMenuType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMenuType(entity1, entity2);
        const compareResult2 = service.compareMenuType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMenuType(entity1, entity2);
        const compareResult2 = service.compareMenuType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
