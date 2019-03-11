import { WeeklyOverviewReflectionClass } from './WeeklyOverviewReflectionClass';

import { WeeklyOverviewClass } from './WeeklyOverviewClass';

export class WeeklyOverview {
	uuid: string
	kw: number
	myWeeklyGoals: string
	firstDayOfWeek: string
	lastDayOfWeek: string
	classes: WeeklyOverviewClass[]
	reflexionClasses: WeeklyOverviewReflectionClass[]
	furtherSteps: string
}