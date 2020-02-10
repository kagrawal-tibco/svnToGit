export interface Notification {
  id: string;
  read: boolean;
  content: string;
  link: string[];
  timestamp: string;
  icon?: string;
  severity: string;
  details: string;
  title?: string;
  data?: any;
}
