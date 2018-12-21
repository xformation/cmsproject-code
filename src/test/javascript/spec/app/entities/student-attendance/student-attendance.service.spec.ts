/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { StudentAttendanceService } from 'app/entities/student-attendance/student-attendance.service';
import { IStudentAttendance, StudentAttendance, Status } from 'app/shared/model/student-attendance.model';

describe('Service Tests', () => {
    describe('StudentAttendance Service', () => {
        let injector: TestBed;
        let service: StudentAttendanceService;
        let httpMock: HttpTestingController;
        let elemDefault: IStudentAttendance;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(StudentAttendanceService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new StudentAttendance(0, currentDate, 'AAAAAAA', Status.ACTIVE, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        attendanceDate: currentDate.format(DATE_FORMAT)
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

            it('should create a StudentAttendance', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        attendanceDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        attendanceDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new StudentAttendance(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a StudentAttendance', async () => {
                const returnedFromService = Object.assign(
                    {
                        attendanceDate: currentDate.format(DATE_FORMAT),
                        sName: 'BBBBBB',
                        status: 'BBBBBB',
                        comments: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        attendanceDate: currentDate
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

            it('should return a list of StudentAttendance', async () => {
                const returnedFromService = Object.assign(
                    {
                        attendanceDate: currentDate.format(DATE_FORMAT),
                        sName: 'BBBBBB',
                        status: 'BBBBBB',
                        comments: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        attendanceDate: currentDate
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

            it('should delete a StudentAttendance', async () => {
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
