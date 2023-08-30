package edu.gatech.seclass.jobcompare6300.model;

public class ComparisonSettings {
	private int yearlySalaryWeight = 1;
	private int yearlyBonusWeight = 1;
	private int stockAwardWeight = 1;
	private int relocationStipendWeight = 1;
	private int holidaysWeight = 1;


	public int getYearlySalaryWeight(){
		return yearlySalaryWeight;
	}
	public void setYearlySalaryWeight(int yearlySalaryWeight){
		this.yearlySalaryWeight = yearlySalaryWeight;
	}
	public int getYearlyBonusWeight(){
		return yearlyBonusWeight;
	}
	public void setYearlyBonusWeight(int yearlyBonusWeight){
		this.yearlyBonusWeight = yearlyBonusWeight;
	}
	public int getStockAwardWeight(){
		return stockAwardWeight;
	}
	public void setStockAwardWeight(int stockAwardWeight){
		this.stockAwardWeight = stockAwardWeight;
	}
	public int getRelocationStipendWeight(){
		return relocationStipendWeight;
	}
	public void setRelocationStipendWeight(int relocationStipendWeight){
		this.relocationStipendWeight = relocationStipendWeight;
	}
	public int getHolidaysWeight(){
		return holidaysWeight;
	}

	public void setHolidaysWeight(int holidaysWeight) {
		this.holidaysWeight = holidaysWeight;
	}

}