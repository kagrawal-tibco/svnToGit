import { User } from '../models/user';

export interface BEUser extends User {
    isDMInstalled: boolean;
    isLockingEnabled: boolean;
    firstName?: string;
    lastName?: string;
    subscriptionId?: string;
}
