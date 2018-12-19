import { Moment } from 'moment';

export const enum Status {
    ACTIVE = 'ACTIVE',
    DEACTIVE = 'DEACTIVE'
}

export interface IStudentAttendance {
    id?: number;
    sName?: string;
    attendanceDate?: Moment;
    status?: Status;
    comments?: string;
    studentYearId?: number;
    departmentsId?: number;
    subjectId?: number;
    semesterId?: number;
    sectionId?: number;
    periodsId?: number;
    studentId?: number;
}

export class StudentAttendance implements IStudentAttendance {
    constructor(
        public id?: number,
        public sName?: string,
        public attendanceDate?: Moment,
        public status?: Status,
        public comments?: string,
        public studentYearId?: number,
        public departmentsId?: number,
        public subjectId?: number,
        public semesterId?: number,
        public sectionId?: number,
        public periodsId?: number,
        public studentId?: number
    ) {}
}