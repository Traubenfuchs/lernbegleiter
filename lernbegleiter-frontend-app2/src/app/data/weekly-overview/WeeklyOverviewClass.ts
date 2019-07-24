import { WeeklyOverviewClassDay } from './WeeklyOverviewClassDay';

export class WeeklyOverviewClass {
	uuid: string
	name: string
	color: string
	days: WeeklyOverviewClassDay[] = [new WeeklyOverviewClassDay(), new WeeklyOverviewClassDay(), new WeeklyOverviewClassDay(), new WeeklyOverviewClassDay(), new WeeklyOverviewClassDay()]
}