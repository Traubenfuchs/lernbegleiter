import { MassRegistrationRow } from './MassRegistrationRow';
export class MassRegistration {
  uuid: string;
  name: string;
  notes: string;
  deletionTime: string;
  amount: number;
  users: MassRegistrationRow[] = [];
}
