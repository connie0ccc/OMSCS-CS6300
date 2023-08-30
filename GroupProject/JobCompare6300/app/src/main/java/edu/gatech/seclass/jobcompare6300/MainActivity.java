package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.gatech.seclass.jobcompare6300.activity.AddJobOfferActivity;
import edu.gatech.seclass.jobcompare6300.activity.AdjustWeightsActivity;
import edu.gatech.seclass.jobcompare6300.activity.CurrentJobActivity;
import edu.gatech.seclass.jobcompare6300.activity.JobRankingActivity;
import edu.gatech.seclass.jobcompare6300.model.SystemMgr;

public class MainActivity extends AppCompatActivity {

    SystemMgr dbHandler;
    private Button currentJobButton;
    private Button newJobOffersButton;
    private Button compareJobOffersButton;
    private Button comparisonSettingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentJobButton = (Button) findViewById(R.id.buttonCurrent);
        newJobOffersButton = (Button) findViewById(R.id.buttonNewOffer);
        compareJobOffersButton = (Button) findViewById(R.id.buttonCompare);
        comparisonSettingsButton = (Button) findViewById(R.id.buttonSetting);

        dbHandler = new SystemMgr(MainActivity.this);
        dbHandler.getWritableDatabase();

        // REMOVE WHEN DONE
        /*
        Job job = new Job();
        job.setTitle("TPM");
        job.setCompany("Microsoft");
        job.setCity("Reston");
        job.setState("Virginia");
        job.setCostOfLivingIndex(Integer.parseInt("233"));
        job.setYearlySalary(Float.parseFloat("152000"));
        job.setYearlyBonus(Float.parseFloat("30000"));
        job.setStockAward(Float.parseFloat("23000"));
        job.setRelocationStipend(Float.parseFloat("12100"));
        job.setHolidays(Integer.parseInt("5"));

        dbHandler.createJobOffer(job);

        Job job2 = new Job();
        job2.setTitle("TPM");
        job2.setCompany("Microsoft");
        job2.setCity("Reston");
        job2.setState("Virginia");
        job2.setCostOfLivingIndex(Integer.parseInt("233"));
        job2.setYearlySalary(Float.parseFloat("200000"));
        job2.setYearlyBonus(Float.parseFloat("50000"));
        job2.setStockAward(Float.parseFloat("30000"));
        job2.setRelocationStipend(Float.parseFloat("12100"));
        job2.setHolidays(Integer.parseInt("5"));

        dbHandler.createJobOffer(job2);
    */
    }

    public void handleClick(View view) {
        switch(view.getId()) {
            case R.id.buttonCurrent:
                startActivity(new Intent(this, CurrentJobActivity.class));
                break;
            case R.id.buttonNewOffer:
                startActivity(new Intent(this, AddJobOfferActivity.class));
                break;
            case R.id.buttonCompare:
                if(dbHandler.listJobs().isEmpty() || dbHandler.listJobs().size() < 2){
                    Toast.makeText(this,"Not enough jobs to compare.",Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(this, JobRankingActivity.class));
                }
                break;
            case R.id.buttonSetting:
                startActivity(new Intent(this, AdjustWeightsActivity.class));
                break;

        }
    }
}
