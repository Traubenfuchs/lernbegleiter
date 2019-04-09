import { Student } from './Student';
import { Class } from './Class';

export class Grade {
	uuid: string = ''
	name: string = ''
	students: Student[] = []
	classes: Class[] = []
}