/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { LegalEntityService } from 'app/entities/legal-entity/legal-entity.service';
import { ILegalEntity, LegalEntity, TypeOfCollege } from 'app/shared/model/legal-entity.model';

describe('Service Tests', () => {
    describe('LegalEntity Service', () => {
        let injector: TestBed;
        let service: LegalEntityService;
        let httpMock: HttpTestingController;
        let elemDefault: ILegalEntity;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(LegalEntityService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new LegalEntity(
                0,
                'AAAAAAA',
                TypeOfCollege.PRIVATE,
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                0,
                currentDate,
                'AAAAAAA',
                0
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateOfIncorporation: currentDate.format(DATE_FORMAT),
                        registrationDate: currentDate.format(DATE_FORMAT),
                        ptRegistrationDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a LegalEntity', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateOfIncorporation: currentDate.format(DATE_FORMAT),
                        registrationDate: currentDate.format(DATE_FORMAT),
                        ptRegistrationDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateOfIncorporation: currentDate,
                        registrationDate: currentDate,
                        ptRegistrationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new LegalEntity(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a LegalEntity', async () => {
                const returnedFromService = Object.assign(
                    {
                        legalNameOfTheCollege: 'BBBBBB',
                        typeOfCollege: 'BBBBBB',
                        dateOfIncorporation: currentDate.format(DATE_FORMAT),
                        registeredOfficeAddress: 'BBBBBB',
                        collegeIdentificationNumber: 'BBBBBB',
                        pan: 'BBBBBB',
                        tan: 'BBBBBB',
                        tanCircleNumber: 'BBBBBB',
                        citTdsLocation: 'BBBBBB',
                        formSignatory: 'BBBBBB',
                        pfNumber: 'BBBBBB',
                        registrationDate: currentDate.format(DATE_FORMAT),
                        esiNumber: 1,
                        ptRegistrationDate: currentDate.format(DATE_FORMAT),
                        ptSignatory: 'BBBBBB',
                        ptNumber: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateOfIncorporation: currentDate,
                        registrationDate: currentDate,
                        ptRegistrationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of LegalEntity', async () => {
                const returnedFromService = Object.assign(
                    {
                        legalNameOfTheCollege: 'BBBBBB',
                        typeOfCollege: 'BBBBBB',
                        dateOfIncorporation: currentDate.format(DATE_FORMAT),
                        registeredOfficeAddress: 'BBBBBB',
                        collegeIdentificationNumber: 'BBBBBB',
                        pan: 'BBBBBB',
                        tan: 'BBBBBB',
                        tanCircleNumber: 'BBBBBB',
                        citTdsLocation: 'BBBBBB',
                        formSignatory: 'BBBBBB',
                        pfNumber: 'BBBBBB',
                        registrationDate: currentDate.format(DATE_FORMAT),
                        esiNumber: 1,
                        ptRegistrationDate: currentDate.format(DATE_FORMAT),
                        ptSignatory: 'BBBBBB',
                        ptNumber: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateOfIncorporation: currentDate,
                        registrationDate: currentDate,
                        ptRegistrationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a LegalEntity', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
