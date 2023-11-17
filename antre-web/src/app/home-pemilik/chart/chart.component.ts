import { Component, OnInit, ViewChild } from '@angular/core';

import {
	ChartComponent,
	ApexAxisChartSeries,
	ApexChart,
	ApexXAxis,
	ApexTitleSubtitle
} from "ng-apexcharts";

export type ChartOptions = {
	series: ApexAxisChartSeries;
	chart: ApexChart;
	xaxis: ApexXAxis;
	title: ApexTitleSubtitle;
};

@Component({
	selector: 'cmp-chart',
	templateUrl: './chart.component.html'
})
export class CmpChart implements OnInit {

	myChart?: ApexCharts;

	ngOnInit(): void {
		this.chart()
	}

	chart() {
		if (!this.myChart) {
			const options: ApexCharts.ApexOptions = {
				colors: ["#1A56DB", "#FDBA8C"],
				series: [
					{
						name: "Pelanggan",
						color: "#37C8C3",
						data: [1, 2, 3, 4, 5, 6, 5]
					}],
				chart: {
					type: "bar",
					height: "220px",
					fontFamily: "Inter, sans-serif",
					toolbar: {
						show: false,
					},
				},
				plotOptions: {
					bar: {
						horizontal: false,
						columnWidth: "50%",
						borderRadiusApplication: "end",
						borderRadius: 8,
					},
				},
				tooltip: {
					shared: true,
					intersect: false,
					style: {
						fontFamily: "Inter, sans-serif",
					},
					//   y: {
					// 		formatter: (val) => {
					// 			return val.toFixed(2) || '0';
					// 		}
					//   }
				},
				states: {
					hover: {
						filter: {
							type: "darken",
							value: 1,
						},
					},
				},
				stroke: {
					show: true,
					width: 0,
					colors: ["transparent"],
				},
				grid: {
					show: false,
					strokeDashArray: 4,
					padding: {
						left: 2,
						right: 2,
						top: -14,
					},
				},
				dataLabels: {
					enabled: false,
				},
				legend: {
					show: false,
				},
				xaxis: {
					floating: false,
					labels: {
						show: true,
						style: {
							fontFamily: "Inter, sans-serif",
							cssClass: 'text-xs font-normal fill-white ',

						},
					},
					axisBorder: {
						show: false,
					},
					axisTicks: {
						show: false,
					},
					categories: ['Senin', 'Selasa', 'Rabu', 'Kamis', 'Jumat', 'Sabtu', 'Minggu'],
				},
				yaxis: {
					show: false,
				},
				fill: {
					opacity: 1,
				},
			};

			if (document.getElementById("chart") && typeof ApexCharts !== "undefined") {
				this.myChart = new ApexCharts(
					document.getElementById("chart"),
					options
				);

				this.myChart.render();
			}
		} else {
			this.myChart?.updateSeries([
				{
					name: "Laba Rugi",
					color: "#F9E79F",
					data: [1, 1, 1, 1, 1, 1]
				}])
		}
	}





}
