import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAuthorityMenuItem } from '../authority-menu-item.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../authority-menu-item.test-samples';

import { AuthorityMenuItemService } from './authority-menu-item.service';

const requireRestSample: IAuthorityMenuItem = {
  ...sampleWithRequiredData,
};

describe('AuthorityMenuItem Service', () => {
  let service: AuthorityMenuItemService;
  let httpMock: HttpTestingController;
  let expectedResult: IAuthorityMenuItem | IAuthorityMenuItem[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AuthorityMenuItemService);
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

    it('should create a AuthorityMenuItem', () => {
      const authorityMenuItem = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(authorityMenuItem).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AuthorityMenuItem', () => {
      const authorityMenuItem = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(authorityMenuItem).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AuthorityMenuItem', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AuthorityMenuItem', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AuthorityMenuItem', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAuthorityMenuItemToCollectionIfMissing', () => {
      it('should add a AuthorityMenuItem to an empty array', () => {
        const authorityMenuItem: IAuthorityMenuItem = sampleWithRequiredData;
        expectedResult = service.addAuthorityMenuItemToCollectionIfMissing([], authorityMenuItem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(authorityMenuItem);
      });

      it('should not add a AuthorityMenuItem to an array that contains it', () => {
        const authorityMenuItem: IAuthorityMenuItem = sampleWithRequiredData;
        const authorityMenuItemCollection: IAuthorityMenuItem[] = [
          {
            ...authorityMenuItem,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAuthorityMenuItemToCollectionIfMissing(authorityMenuItemCollection, authorityMenuItem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AuthorityMenuItem to an array that doesn't contain it", () => {
        const authorityMenuItem: IAuthorityMenuItem = sampleWithRequiredData;
        const authorityMenuItemCollection: IAuthorityMenuItem[] = [sampleWithPartialData];
        expectedResult = service.addAuthorityMenuItemToCollectionIfMissing(authorityMenuItemCollection, authorityMenuItem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(authorityMenuItem);
      });

      it('should add only unique AuthorityMenuItem to an array', () => {
        const authorityMenuItemArray: IAuthorityMenuItem[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const authorityMenuItemCollection: IAuthorityMenuItem[] = [sampleWithRequiredData];
        expectedResult = service.addAuthorityMenuItemToCollectionIfMissing(authorityMenuItemCollection, ...authorityMenuItemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const authorityMenuItem: IAuthorityMenuItem = sampleWithRequiredData;
        const authorityMenuItem2: IAuthorityMenuItem = sampleWithPartialData;
        expectedResult = service.addAuthorityMenuItemToCollectionIfMissing([], authorityMenuItem, authorityMenuItem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(authorityMenuItem);
        expect(expectedResult).toContain(authorityMenuItem2);
      });

      it('should accept null and undefined values', () => {
        const authorityMenuItem: IAuthorityMenuItem = sampleWithRequiredData;
        expectedResult = service.addAuthorityMenuItemToCollectionIfMissing([], null, authorityMenuItem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(authorityMenuItem);
      });

      it('should return initial array if no AuthorityMenuItem is added', () => {
        const authorityMenuItemCollection: IAuthorityMenuItem[] = [sampleWithRequiredData];
        expectedResult = service.addAuthorityMenuItemToCollectionIfMissing(authorityMenuItemCollection, undefined, null);
        expect(expectedResult).toEqual(authorityMenuItemCollection);
      });
    });

    describe('compareAuthorityMenuItem', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAuthorityMenuItem(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAuthorityMenuItem(entity1, entity2);
        const compareResult2 = service.compareAuthorityMenuItem(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAuthorityMenuItem(entity1, entity2);
        const compareResult2 = service.compareAuthorityMenuItem(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAuthorityMenuItem(entity1, entity2);
        const compareResult2 = service.compareAuthorityMenuItem(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
