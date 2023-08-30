package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.seclass.jobcompare6300.activity.JobRankingActivity;
import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.model.SystemMgr;

public class JobComparisonActivity extends AppCompatActivity {

    private TextView jobTitle1;
    private TextView companyName1;
    private TextView location1;
    private TextView yearlySalary1;
    private TextView yearlyBonus1;
    private TextView RSU1;
    private TextView relocationStipend1;
    private TextView holidays1;
    private TextView jobTitle2;
    private TextView companyName2;
    private TextView location2;
    private TextView yearlySalary2;
    private TextView yearlyBonus2;
    private TextView RSU2;
    private TextView relocationStipend2;
    private TextView holidays2;

    private Button returnHomeButton,anotherComparisonButton;
    SystemMgr dbHandler = new SystemMgr(JobComparisonActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_comparison);
        jobTitle1 = (TextView) findViewById(R.id.titleA);
        companyName1 = (TextView) findViewById(R.id.companyA);
        location1 = (TextView) findViewById(R.id.locationA);
        yearlySalary1 = (TextView) findViewById(R.id.yearlySalaryA);
        yearlyBonus1 = (TextView) findViewById(R.id.yearlyBonusA);
        RSU1 = (TextView) findViewById(R.id.RSUA);
        relocationStipend1 = (TextView) findViewById(R.id.RelocationStipendA);
        holidays1 = (TextView) findViewById(R.id.personalDayA);
        jobTitle2 = (TextView) findViewById(R.id.titleB);
        companyName2 = (TextView) findViewById(R.id.companyB);
        location2 = (TextView) findViewById(R.id.locationB);
        yearlySalary2 = (TextView) findViewById(R.id.yearlySalaryB);
        yearlyBonus2 = (TextView) findViewById(R.id.yearlyBonusB);
        RSU2 = (TextView) findViewById(R.id.RSUB);
        relocationStipend2 = (TextView) findViewById(R.id.RelocationStipendB);
        holidays2 = (TextView) findViewById(R.id.personalDayB);

        returnHomeButton = (Button) findViewById(R.id.returnHome);
        anotherComparisonButton = (Button) findViewById(R.id.buttonAnotherComparison);

        // get job1 and job2
        Intent intent = getIntent();
        int jobId1 = (int) intent.getLongExtra("comparisonJobID1",0);
        int jobId2 = (int) intent.getLongExtra("comparisonJobID2",0);
        Job job1 = dbHandler.getJob(jobId1);
        Job job2 = dbHandler.getJob(jobId2);

        // set text to display
        jobTitle1.setText(job1.getTitle());
        companyName1.setText(job1.getCompany());
        location1.setText(job1.getCity()+", "+job1.getState());
        float ays1 = job1.getYearlySalary()*100/job1.getCostOfLivingIndex();
        yearlySalary1.setText(Float.toString(ays1));
        float ayb1 = job1.getYearlyBonus()*100/job1.getCostOfLivingIndex();
        yearlyBonus1.setText(Float.toString(ayb1));
        RSU1.setText(Float.toString(job1.getStockAward()));
        relocationStipend1.setText(Float.toString(job1.getRelocationStipend()));
        holidays1.setText(Integer.toString(job1.getHolidays()));

        jobTitle2.setText(job2.getTitle());
        companyName2.setText(job2.getCompany());
        location2.setText(job2.getCity()+", "+job2.getState());
        float ays2 = job2.getYearlySalary()*100/job2.getCostOfLivingIndex();
        yearlySalary2.setText(Float.toString(ays2));
        float ayb2 = job2.getYearlyBonus()*100/job2.getCostOfLivingIndex();
        yearlyBonus2.setText(Float.toString(ayb2));
        RSU2.setText(Float.toString(job2.getStockAward()));
        relocationStipend2.setText(Float.toString(job2.getRelocationStipend()));
        holidays2.setText(Integer.toString(job2.getHolidays()));
    }

    public void handleClick(View view){

        switch (view.getId()) {
            case R.id.buttonAnotherComparison:
                Intent intent = new Intent(this, JobRankingActivity.class);
                startActivity(intent);
                break;
            case R.id.returnHome:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}