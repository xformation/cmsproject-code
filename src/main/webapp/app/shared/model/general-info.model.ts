export interface IGeneralInfo {
    id?: number;
    shortName?: string;
    logo?: number;
    backgroundImage?: number;
    instructionInformation?: string;
}

export class GeneralInfo implements IGeneralInfo {
    constructor(
        public id?: number,
        public shortName?: string,
        public logo?: number,
        public backgroundImage?: number,
        public instructionInformation?: string
    ) {}
}
