export interface ICollegeBranches {
    id?: number;
    branchName?: string;
    description?: string;
    collegeHead?: string;
}

export class CollegeBranches implements ICollegeBranches {
    constructor(public id?: number, public branchName?: string, public description?: string, public collegeHead?: string) {}
}
