package edu.gatech.seclass.jobcompare6300.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Comparator;

import edu.gatech.seclass.jobcompare6300.model.ComparisonSettings;
import edu.gatech.seclass.jobcompare6300.model.Job;

public class SystemMgr extends SQLiteOpenHelper
{

    private static final String DB_NAME = "jobcomparedb";

    private static final int DB_VERSION = 1;

    private static final String ID_COL = "id";

    //TABLE NAMES
    private static final String JOB_TABLE_NAME = "Jobs";

    private static final String COMPARISON_TABLE_NAME = "ComparisonSettings";

    //JOBS TABLE COLUMN NAMES
    private static final String JOB_TITLE_COLUMN_NAME = "Title";

    private static final String JOB_COMPANY_COLUMN_NAME = "Company";

    private static final String JOB_CITY_COLUMN_NAME = "City";

    private static final String JOB_STATE_COLUMN_NAME = "State";

    private static final String JOB_COLI_COLUMN_NAME = "CostOfLivingIndex";

    private static final String JOB_SALARY_COLUMN_NAME = "Salary";

    private static final String JOB_BONUS_COLUMN_NAME = "Bonus";

    private static final String JOB_STOCK_COLUMN_NAME = "StockAward";

    private static final String JOB_RELOCATION_COLUMN_NAME = "RelocationStipend";

    private static final String JOB_HOLIDAYS_COLUMN_NAME = "Holidays";

    private static final String CURRENT_JOB_COLUMN_NAME = "CurrentJob";

    //COMPARISONSETTINGS TABLE COLUMN NAMES
    private static final String COMPARISON_SALARY_COLUMN_NAME = "SalaryWeight";

    private static final String COMPARISON_BONUS_COLUMN_NAME = "BonusWeight";

    private static final String COMPARISON_STOCK_COLUMN_NAME = "StockWeight";

    private static final String COMPARISON_RELOCATION_COLUMN_NAME = "RelocationWeight";

    private static final String COMPARISON_HOLIDAYS_COLUMN_NAME = "HolidaysWeight";

    public SystemMgr(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Jobs table
        String query = "CREATE TABLE " + JOB_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + JOB_TITLE_COLUMN_NAME + " TEXT,"
                + JOB_COMPANY_COLUMN_NAME + " TEXT,"
                + JOB_CITY_COLUMN_NAME + " TEXT,"
                + JOB_STATE_COLUMN_NAME + " TEXT,"
                + JOB_COLI_COLUMN_NAME + " INTEGER,"
                + JOB_SALARY_COLUMN_NAME + " FLOAT,"
                + JOB_BONUS_COLUMN_NAME + " FLOAT,"
                + JOB_STOCK_COLUMN_NAME + " FLOAT,"
                + JOB_RELOCATION_COLUMN_NAME + " FLOAT,"
                + JOB_HOLIDAYS_COLUMN_NAME + " INTEGER,"
                + CURRENT_JOB_COLUMN_NAME + " BOOLEAN);";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);

        //blocks creation of another current job - there should only be one current job
        //query = "CREATE UNIQUE INDEX index_current_job ON " + JOB_TABLE_NAME + " (" + CURRENT_JOB_COLUMN_NAME + ");";
        //db.execSQL(query);

        query = "CREATE TABLE " + COMPARISON_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COMPARISON_SALARY_COLUMN_NAME + " INTEGER DEFAULT 1,"
                + COMPARISON_BONUS_COLUMN_NAME + " INTEGER DEFAULT 1,"
                + COMPARISON_STOCK_COLUMN_NAME + " INTEGER DEFAULT 1,"
                + COMPARISON_RELOCATION_COLUMN_NAME + " INTEGER DEFAULT 1,"
                + COMPARISON_HOLIDAYS_COLUMN_NAME + " INTEGER DEFAULT 1);";

        db.execSQL(query);

        query = "INSERT INTO " + COMPARISON_TABLE_NAME + " DEFAULT VALUES;";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private long createJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(JOB_TITLE_COLUMN_NAME, job.getTitle());
        values.put(JOB_COMPANY_COLUMN_NAME, job.getCompany());
        values.put(JOB_CITY_COLUMN_NAME, job.getCity());
        values.put(JOB_STATE_COLUMN_NAME, job.getState());
        values.put(JOB_COLI_COLUMN_NAME, job.getCostOfLivingIndex());
        values.put(JOB_SALARY_COLUMN_NAME, job.getYearlySalary());
        values.put(JOB_BONUS_COLUMN_NAME, job.getYearlyBonus());
        values.put(JOB_STOCK_COLUMN_NAME, job.getStockAward());
        values.put(JOB_RELOCATION_COLUMN_NAME, job.getRelocationStipend());
        values.put(JOB_HOLIDAYS_COLUMN_NAME, job.getHolidays());
        values.put(CURRENT_JOB_COLUMN_NAME, job.isCurrentJob());


        long rowId = db.insert(JOB_TABLE_NAME, null, values);
        db.close();

        return rowId;
    }

    public long createCurrentJob(Job job) {
        job.setCurrentJob(true);
        return this.createJob(job);
    }

    public int updateCurrentJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(JOB_TITLE_COLUMN_NAME, job.getTitle());
        values.put(JOB_COMPANY_COLUMN_NAME, job.getCompany());
        values.put(JOB_CITY_COLUMN_NAME, job.getCity());
        values.put(JOB_STATE_COLUMN_NAME, job.getState());
        values.put(JOB_COLI_COLUMN_NAME, job.getCostOfLivingIndex());
        values.put(JOB_SALARY_COLUMN_NAME, job.getYearlySalary());
        values.put(JOB_BONUS_COLUMN_NAME, job.getYearlyBonus());
        values.put(JOB_STOCK_COLUMN_NAME, job.getStockAward());
        values.put(JOB_RELOCATION_COLUMN_NAME, job.getRelocationStipend());
        values.put(JOB_HOLIDAYS_COLUMN_NAME, job.getHolidays());

        String selection = CURRENT_JOB_COLUMN_NAME + " = 1;";

        //String[] selectionArgs = { "true" };

        int count = db.update(
                JOB_TABLE_NAME,
                values,
                selection,
                null);

        db.close();

        return count;
    }

    public long createJobOffer(Job job) {
        job.setCurrentJob(false);
        return this.createJob(job);
    }

    public void updateComparisonSettings(ComparisonSettings settings) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COMPARISON_SALARY_COLUMN_NAME, settings.getYearlySalaryWeight());
        values.put(COMPARISON_BONUS_COLUMN_NAME, settings.getYearlyBonusWeight());
        values.put(COMPARISON_STOCK_COLUMN_NAME, settings.getStockAwardWeight());
        values.put(COMPARISON_RELOCATION_COLUMN_NAME, settings.getRelocationStipendWeight());
        values.put(COMPARISON_HOLIDAYS_COLUMN_NAME, settings.getHolidaysWeight());

        int count = db.update(
                COMPARISON_TABLE_NAME,
                values,
                null,
                null);

        db.close();

    }

    @SuppressLint("Range")
    public ComparisonSettings getComparisonSettings() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor results;

        ComparisonSettings settings = new ComparisonSettings();

        results = db.query(COMPARISON_TABLE_NAME, null, null, null, null, null, null);

        if(results.moveToNext()) {
            settings.setYearlySalaryWeight(results.getInt(results.getColumnIndex(COMPARISON_SALARY_COLUMN_NAME)));
            settings.setYearlyBonusWeight(results.getInt(results.getColumnIndex(COMPARISON_BONUS_COLUMN_NAME)));
            settings.setStockAwardWeight(results.getInt(results.getColumnIndex(COMPARISON_STOCK_COLUMN_NAME)));
            settings.setRelocationStipendWeight(results.getInt(results.getColumnIndex(COMPARISON_RELOCATION_COLUMN_NAME)));
            settings.setHolidaysWeight(results.getInt(results.getColumnIndex(COMPARISON_HOLIDAYS_COLUMN_NAME)));
        } else {
            return null;
        }

        return settings;
    }

    public ArrayList<Job> compareJobs(Job job1, Job job2) {

        float job1Score = calcScore(job1);
        float job2Score = calcScore(job2);

        ArrayList<Job> rankedList = new ArrayList<Job>();


        if(job1Score > job2Score) {
            rankedList.add(0, job1);
            rankedList.add(1, job2);
        } else {
            rankedList.add(0, job2);
            rankedList.add(1, job1);
        }

        return rankedList;
    }

    @SuppressLint("Range")
    public ArrayList<Job> listJobs() {
        ArrayList<Job> jobList = new ArrayList<Job>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results;

        results = db.query(JOB_TABLE_NAME, null, null, null, null, null, null);

        while(results.moveToNext()) {
            Job job = new Job();
            job.setJobID(results.getInt(results.getColumnIndex(ID_COL)));
            job.setTitle(results.getString(results.getColumnIndex(JOB_TITLE_COLUMN_NAME)));
            job.setCompany(results.getString(results.getColumnIndex(JOB_COMPANY_COLUMN_NAME)));
            job.setCity(results.getString(results.getColumnIndex(JOB_CITY_COLUMN_NAME)));
            job.setState(results.getString(results.getColumnIndex(JOB_STATE_COLUMN_NAME)));
            job.setCostOfLivingIndex(results.getInt(results.getColumnIndex(JOB_COLI_COLUMN_NAME)));
            job.setYearlySalary(results.getFloat(results.getColumnIndex(JOB_SALARY_COLUMN_NAME)));
            job.setYearlyBonus(results.getFloat(results.getColumnIndex(JOB_BONUS_COLUMN_NAME)));
            job.setStockAward(results.getFloat(results.getColumnIndex(JOB_STOCK_COLUMN_NAME)));
            job.setRelocationStipend(results.getFloat(results.getColumnIndex(JOB_RELOCATION_COLUMN_NAME)));
            job.setHolidays(results.getInt(results.getColumnIndex(JOB_HOLIDAYS_COLUMN_NAME)));
            job.setCurrentJob(results.getInt(results.getColumnIndex(CURRENT_JOB_COLUMN_NAME)) > 0);

            jobList.add(job);
        }

        //get job scores
        for(Job j : jobList) {
            j.setJobScore(calcScore(j));
        }

        //sort descending
        jobList.sort(Comparator.comparing(Job::getJobScore).reversed());

        /*
        for(Job j : jobList) {
            System.out.println(j.getJobScore());
        }
        */

        return jobList;
    }

    @SuppressLint("Range")
    public Job getCurrentJob() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results;
        Job job = new Job();

        String selection = CURRENT_JOB_COLUMN_NAME + " = 1;";

        results = db.query(JOB_TABLE_NAME, null, selection, null, null, null, null);

        if(results.moveToNext()) {

            job.setJobID(results.getInt(results.getColumnIndex(ID_COL)));
            job.setTitle(results.getString(results.getColumnIndex(JOB_TITLE_COLUMN_NAME)));
            job.setCompany(results.getString(results.getColumnIndex(JOB_COMPANY_COLUMN_NAME)));
            job.setCity(results.getString(results.getColumnIndex(JOB_CITY_COLUMN_NAME)));
            job.setState(results.getString(results.getColumnIndex(JOB_STATE_COLUMN_NAME)));
            job.setCostOfLivingIndex(results.getInt(results.getColumnIndex(JOB_COLI_COLUMN_NAME)));
            job.setYearlySalary(results.getFloat(results.getColumnIndex(JOB_SALARY_COLUMN_NAME)));
            job.setYearlyBonus(results.getFloat(results.getColumnIndex(JOB_BONUS_COLUMN_NAME)));
            job.setStockAward(results.getFloat(results.getColumnIndex(JOB_STOCK_COLUMN_NAME)));
            job.setRelocationStipend(results.getFloat(results.getColumnIndex(JOB_RELOCATION_COLUMN_NAME)));
            job.setHolidays(results.getInt(results.getColumnIndex(JOB_HOLIDAYS_COLUMN_NAME)));
            job.setCurrentJob(true);

            System.out.println("getCurrentJob() :" + job.getJobID());

            return job;
        } else {
            return null;
        }
    }

    @SuppressLint("Range")
    public Job getJob(Integer ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor results;
        Job job = new Job();

        String selection = ID_COL + " = " + ID + ";";

        results = db.query(JOB_TABLE_NAME, null, selection, null, null, null, null);

        if(results.moveToNext()) {

            job.setJobID(results.getInt(results.getColumnIndex(ID_COL)));
            job.setTitle(results.getString(results.getColumnIndex(JOB_TITLE_COLUMN_NAME)));
            job.setCompany(results.getString(results.getColumnIndex(JOB_COMPANY_COLUMN_NAME)));
            job.setCity(results.getString(results.getColumnIndex(JOB_CITY_COLUMN_NAME)));
            job.setState(results.getString(results.getColumnIndex(JOB_STATE_COLUMN_NAME)));
            job.setCostOfLivingIndex(results.getInt(results.getColumnIndex(JOB_COLI_COLUMN_NAME)));
            job.setYearlySalary(results.getFloat(results.getColumnIndex(JOB_SALARY_COLUMN_NAME)));
            job.setYearlyBonus(results.getFloat(results.getColumnIndex(JOB_BONUS_COLUMN_NAME)));
            job.setStockAward(results.getFloat(results.getColumnIndex(JOB_STOCK_COLUMN_NAME)));
            job.setRelocationStipend(results.getFloat(results.getColumnIndex(JOB_RELOCATION_COLUMN_NAME)));
            job.setHolidays(results.getInt(results.getColumnIndex(JOB_HOLIDAYS_COLUMN_NAME)));

            return job;

        } else {
            return null;
        }
    }

    private float calcScore(Job job) {
        ComparisonSettings settings = getComparisonSettings();

        float adjustedYearlySalary = (job.getYearlySalary()*100) / job.getCostOfLivingIndex();
        float adjustedYearlyBonus = (job.getYearlyBonus()*100) / job.getCostOfLivingIndex();
        float stockAward = job.getStockAward();
        float relocationStipend = job.getRelocationStipend();
        int yearlyHolidays = job.getHolidays();
        float salaryWeight = (float) settings.getYearlySalaryWeight();
        float bonusWeight = (float) settings.getYearlyBonusWeight();
        float stockWeight = (float) settings.getStockAwardWeight();
        float relocationWeight = (float) settings.getRelocationStipendWeight();
        float holidaysWeight = (float) settings.getHolidaysWeight();
        float score;
        float sumOfWeights = salaryWeight + bonusWeight + stockWeight + relocationWeight + holidaysWeight;

        /*
        System.out.println("In calcScore().  adjustYearlySalary = : " + adjustedYearlySalary);
        System.out.println("In calcScore().  adjustedYearlyBonus = : " + adjustedYearlyBonus);
        System.out.println("In calcScore().  stockAward = : " + stockAward);
        System.out.println("In calcScore().  relocationStipend = : " + relocationStipend);
        System.out.println("In calcScore().  yearlyHolidays = : " + yearlyHolidays);
        System.out.println("In calcScore().  salaryWeight = : " + salaryWeight);
        System.out.println("In calcScore().  bonusWeight = : " + bonusWeight);
        System.out.println("In calcScore().  stockWeight = : " + stockWeight);
        System.out.println("In calcScore().  relocationWeight = : " + relocationWeight);
        System.out.println("In calcScore().  holidaysWeight = : " + holidaysWeight);
        System.out.println("In calcScore().  sumOfWeights = : " + sumOfWeights);
        */
        score = (salaryWeight/sumOfWeights * adjustedYearlySalary) + (bonusWeight/sumOfWeights * adjustedYearlyBonus) + (stockWeight/sumOfWeights * (stockAward/4)) + (relocationWeight/sumOfWeights * relocationStipend) + (holidaysWeight/sumOfWeights * (yearlyHolidays * adjustedYearlySalary / 260));

        //System.out.println("In calcScore().  score = : " + score);

        return score;
    }
}
