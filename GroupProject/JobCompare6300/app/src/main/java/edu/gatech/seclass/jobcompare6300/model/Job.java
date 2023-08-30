package edu.gatech.seclass.jobcompare6300.model;

public class Job {
    private String title;
    private String company;
    private String city;
    private String state;
    private int costOfLivingIndex;
    private float yearlySalary;
    private float yearlyBonus;
    private float stockAward;
    private float relocationStipend;
    private int holidays;
    private boolean currentJob;
    private int jobID;
    private float jobScore;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCostOfLivingIndex() {
        return costOfLivingIndex;
    }

    public void setCostOfLivingIndex(int costOfLivingIndex) {
        this.costOfLivingIndex = costOfLivingIndex;
    }

    public float getYearlySalary() {
        return yearlySalary;
    }

    public void setYearlySalary(float yearlySalary) {
        this.yearlySalary = yearlySalary;
    }

    public float getYearlyBonus() {
        return yearlyBonus;
    }

    public void setYearlyBonus(float yearlyBonus) {
        this.yearlyBonus = yearlyBonus;
    }

    public float getStockAward() {
        return stockAward;
    }

    public void setStockAward(float stockAward) {
        this.stockAward = stockAward;
    }

    public float getRelocationStipend() {
        return relocationStipend;
    }

    public void setRelocationStipend(float relocationStipend) {
        this.relocationStipend = relocationStipend;
    }

    public int getHolidays() {
        return holidays;
    }

    public void setHolidays(int holidays) {
        this.holidays = holidays;
    }

    public boolean isCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(boolean currentJob) {
        this.currentJob = currentJob;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public int getJobID() { return jobID; }

    public void setJobScore(float jobScore) {
        this.jobScore = jobScore;
    }

    public float getJobScore() { return jobScore; }

}
