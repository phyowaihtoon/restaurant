import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDiningTable } from '../dining-table.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../dining-table.test-samples';

import { DiningTableService } from './dining-table.service';

const requireRestSample: IDiningTable = {
  ...sampleWithRequiredData,
};

describe('DiningTable Service', () => {
  let service: DiningTableService;
  let httpMock: HttpTestingController;
  let expectedResult: IDiningTable | IDiningTable[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DiningTableService);
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

    it('should create a DiningTable', () => {
      const diningTable = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(diningTable).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DiningTable', () => {
      const diningTable = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(diningTable).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DiningTable', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DiningTable', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DiningTable', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDiningTableToCollectionIfMissing', () => {
      it('should add a DiningTable to an empty array', () => {
        const diningTable: IDiningTable = sampleWithRequiredData;
        expectedResult = service.addDiningTableToCollectionIfMissing([], diningTable);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(diningTable);
      });

      it('should not add a DiningTable to an array that contains it', () => {
        const diningTable: IDiningTable = sampleWithRequiredData;
        const diningTableCollection: IDiningTable[] = [
          {
            ...diningTable,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDiningTableToCollectionIfMissing(diningTableCollection, diningTable);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DiningTable to an array that doesn't contain it", () => {
        const diningTable: IDiningTable = sampleWithRequiredData;
        const diningTableCollection: IDiningTable[] = [sampleWithPartialData];
        expectedResult = service.addDiningTableToCollectionIfMissing(diningTableCollection, diningTable);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(diningTable);
      });

      it('should add only unique DiningTable to an array', () => {
        const diningTableArray: IDiningTable[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const diningTableCollection: IDiningTable[] = [sampleWithRequiredData];
        expectedResult = service.addDiningTableToCollectionIfMissing(diningTableCollection, ...diningTableArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const diningTable: IDiningTable = sampleWithRequiredData;
        const diningTable2: IDiningTable = sampleWithPartialData;
        expectedResult = service.addDiningTableToCollectionIfMissing([], diningTable, diningTable2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(diningTable);
        expect(expectedResult).toContain(diningTable2);
      });

      it('should accept null and undefined values', () => {
        const diningTable: IDiningTable = sampleWithRequiredData;
        expectedResult = service.addDiningTableToCollectionIfMissing([], null, diningTable, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(diningTable);
      });

      it('should return initial array if no DiningTable is added', () => {
        const diningTableCollection: IDiningTable[] = [sampleWithRequiredData];
        expectedResult = service.addDiningTableToCollectionIfMissing(diningTableCollection, undefined, null);
        expect(expectedResult).toEqual(diningTableCollection);
      });
    });

    describe('compareDiningTable', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDiningTable(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDiningTable(entity1, entity2);
        const compareResult2 = service.compareDiningTable(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDiningTable(entity1, entity2);
        const compareResult2 = service.compareDiningTable(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDiningTable(entity1, entity2);
        const compareResult2 = service.compareDiningTable(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
