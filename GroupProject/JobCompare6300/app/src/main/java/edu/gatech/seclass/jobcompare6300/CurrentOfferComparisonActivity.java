package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.model.SystemMgr;

public class CurrentOfferComparisonActivity extends AppCompatActivity {
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

    private Button returnHomeButton;
    SystemMgr dbHandler = new SystemMgr(CurrentOfferComparisonActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_offer_comparison);
        jobTitle1 = (TextView) findViewById(R.id.titleA);
        companyName1 = (TextView) findViewById(R.id.companyA);
        location1 = (TextView) findViewById(R.id.locationA);
        yearlySalary1 = (TextView) findViewById(R.id.yearlySalaryA);
        yearlyBonus1 = (TextView) findViewById(R.id.yearlyBonusA);
        RSU1 = (TextView) findViewById(R.id.RSUA);
        relocationStipend1 = (TextView) findViewById(R.id.relocationStipendA);
        holidays1 = (TextView) findViewById(R.id.personalDayA);
        jobTitle2 = (TextView) findViewById(R.id.titleB);
        companyName2 = (TextView) findViewById(R.id.companyB);
        location2 = (TextView) findViewById(R.id.locationB);
        yearlySalary2 = (TextView) findViewById(R.id.yearlySalaryB);
        yearlyBonus2 = (TextView) findViewById(R.id.yearlyBonusB);
        RSU2 = (TextView) findViewById(R.id.RSUB);
        relocationStipend2 = (TextView) findViewById(R.id.relocationStipendB);
        holidays2 = (TextView) findViewById(R.id.personalDayB);

        returnHomeButton = (Button) findViewById(R.id.returnHome);

        // get current job and job offer just saved
        Job currentJob = dbHandler.getCurrentJob();
        Intent intent = getIntent();
        int thisJobOfferId = (int) intent.getLongExtra("thisJobOfferId",0);
        Job thisJobOffer = dbHandler.getJob(thisJobOfferId);

        // set text to display
        jobTitle1.setText(currentJob.getTitle());
        companyName1.setText(currentJob.getCompany());
        location1.setText(currentJob.getCity()+", "+currentJob.getState());
        float ays1 = currentJob.getYearlySalary()*100/currentJob.getCostOfLivingIndex();
        yearlySalary1.setText(Float.toString(ays1));
        float ayb1 = currentJob.getYearlyBonus()*100/currentJob.getCostOfLivingIndex();
        yearlyBonus1.setText(Float.toString(ayb1));
        RSU1.setText(Float.toString(currentJob.getStockAward()));
        relocationStipend1.setText(Float.toString(currentJob.getRelocationStipend()));
        holidays1.setText(Integer.toString(currentJob.getHolidays()));

        jobTitle2.setText(thisJobOffer.getTitle());
        companyName2.setText(thisJobOffer.getCompany());
        location2.setText(thisJobOffer.getCity()+", "+thisJobOffer.getState());
        float ays2 = thisJobOffer.getYearlySalary()*100/thisJobOffer.getCostOfLivingIndex();
        yearlySalary2.setText(Float.toString(ays2));
        float ayb2 = thisJobOffer.getYearlyBonus()*100/thisJobOffer.getCostOfLivingIndex();
        yearlyBonus2.setText(Float.toString(ayb2));
        RSU2.setText(Float.toString(thisJobOffer.getStockAward()));
        relocationStipend2.setText(Float.toString(thisJobOffer.getRelocationStipend()));
        holidays2.setText(Integer.toString(thisJobOffer.getHolidays()));
    }

    public void handleClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
