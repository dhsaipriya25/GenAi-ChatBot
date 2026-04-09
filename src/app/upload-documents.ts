import { UserReg } from "./user-reg";
export class UploadDocuments {
    id !: string;
    name !: string[];
    type ! :string[];
    prompt !: string;
    applicationName !: string;
    data !:Uint8Array[];
    url !: string;
    fileLink !: string;
    apiKey !: string;
    apiProvider !: string;
    user !: UserReg;
    // userName !: string;

}
