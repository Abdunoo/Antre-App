export interface DtoHistory {
	map(arg0: (item: { label: any; value: any; }) => { x: any; y: any; }): unknown;
	label: string;
	value: number;
}