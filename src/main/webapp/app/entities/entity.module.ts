import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CmsprojectStudentYearModule } from './student-year/student-year.module';
import { CmsprojectSectionModule } from './section/section.module';
import { CmsprojectPeriodsModule } from './periods/periods.module';
import { CmsprojectSubjectModule } from './subject/subject.module';
import { CmsprojectTeacherModule } from './teacher/teacher.module';
import { CmsprojectStudentModule } from './student/student.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CmsprojectStudentYearModule,
        CmsprojectSectionModule,
        CmsprojectPeriodsModule,
        CmsprojectSubjectModule,
        CmsprojectTeacherModule,
        CmsprojectStudentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CmsprojectEntityModule {}
