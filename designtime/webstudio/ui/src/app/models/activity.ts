export type ActivityType = 'AUDIT' | 'COMMIT';

export class Activity {
  id: string;
  projectName: string;
  userName: string;
  link?: string[];
  timestamp: number;
  icon?: string;
  severity: string;
  details: string;
  title?: string;
  requiresAction: boolean;
  type: ActivityType;
  subType?: string;
  userImg?: string;
  summary?: string;
  data?: any;
  state = 'unfiltered'; // the filtered state of the activity
}

export class MultiActivity extends Activity {
  activities: Activity[];
}
