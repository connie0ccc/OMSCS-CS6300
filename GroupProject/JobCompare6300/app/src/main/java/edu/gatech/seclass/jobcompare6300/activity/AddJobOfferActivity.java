package edu.gatech.seclass.jobcompare6300.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.JobSavedActivity;
import edu.gatech.seclass.jobcompare6300.MainActivity;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.model.SystemMgr;

public class AddJobOfferActivity extends AppCompatActivity {


    private EditText jobTitle;
    private EditText companyName;
    private EditText city;
    private EditText state;
    private EditText costOfLivingIndex;
    private EditText yearlySalary;
    private EditText yearlyBonus;
    private EditText stockAward;
    private EditText relocationStipend;
    private EditText holidays;
    private Button saveButton;
    private Button cancelButton;

    private SystemMgr dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_offer);

        jobTitle = (EditText) findViewById(R.id.jobTitle);
        companyName = (EditText) findViewById(R.id.companyName);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        costOfLivingIndex = (EditText) findViewById(R.id.costOfLivingIndex);
        yearlySalary = (EditText) findViewById(R.id.yearlySalary);
        yearlyBonus = (EditText) findViewById(R.id.yearlyBonus);
        stockAward = (EditText) findViewById(R.id.stockAward);
        relocationStipend = (EditText) findViewById(R.id.relocationStipend);
        holidays = (EditText) findViewById(R.id.holidays);
        saveButton = (Button) findViewById(R.id.buttonSaveJobOffer);
        cancelButton = (Button) findViewById(R.id.buttonCancelJobOffer);


        dbHandler = new SystemMgr(AddJobOfferActivity.this);

    }

    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSaveJobOffer:
                boolean error = false;
                long thisJobOfferId = -1;
                saveButton.setBackgroundColor(Color.GRAY);

                String jobTitleString = jobTitle.getText().toString();
                String companyNameString = companyName.getText().toString();
                String cityString = city.getText().toString();
                String stateString = state.getText().toString();
                String costOfLivingIndexString = costOfLivingIndex.getText().toString();
                String yearlySalaryString = yearlySalary.getText().toString();
                String yearlyBonusString = yearlyBonus.getText().toString();
                String stockAwardString = stockAward.getText().toString();
                String relocationStipendString = relocationStipend.getText().toString();
                String yearlyHolidaysString = holidays.getText().toString();

                if(jobTitleString.trim().length() == 0 || companyNameString.trim().length() == 0 || cityString.trim().length() == 0 ||
                        stateString.trim().length() == 0 || costOfLivingIndexString.trim().length() == 0 || yearlySalaryString.trim().length() == 0 ||
                        yearlyBonusString.trim().length() == 0 || stockAwardString.trim().length() == 0 || relocationStipendString.trim().length() == 0 ||
                        yearlyHolidaysString.trim().length() == 0) {
                    {
                        //saveButton.setError("All fields required.");
                        Toast.makeText(AddJobOfferActivity.this,"All fields need to be completed.",Toast.LENGTH_SHORT).show();
                        error = true;
                    }
                } else if(Float.parseFloat(relocationStipendString) < 0 || Float.parseFloat(relocationStipendString) > 25000) {
                    Toast.makeText(AddJobOfferActivity.this,"Relocation Stipend should be in the range 0 to 25000.",Toast.LENGTH_SHORT).show();
                    error = true;
                } else if(Integer.parseInt(yearlyHolidaysString) < 0 || Integer.parseInt(yearlyHolidaysString) > 20) {
                    Toast.makeText(AddJobOfferActivity.this,"Personal Choice Holidays should be in the range 0 to 20.",Toast.LENGTH_SHORT).show();
                    error = true;
                } else if(Integer.parseInt(costOfLivingIndexString) <= 0) {
                Toast.makeText(AddJobOfferActivity.this,"Cost of living should be greater then 0.",Toast.LENGTH_SHORT).show();
                error = true;
                 }

                if (!error) {
                    Job job = new Job();
                    job.setTitle(jobTitleString);
                    job.setCompany(companyNameString);
                    job.setCity(cityString);
                    job.setState(stateString);
                    job.setCostOfLivingIndex(Integer.parseInt(costOfLivingIndexString));
                    job.setYearlySalary(Float.parseFloat(yearlySalaryString));
                    job.setYearlyBonus(Float.parseFloat(yearlyBonusString));
                    job.setStockAward(Float.parseFloat(stockAwardString));
                    job.setRelocationStipend(Float.parseFloat(relocationStipendString));
                    job.setHolidays(Integer.parseInt(yearlyHolidaysString));
                    job.setCurrentJob(false);
                    thisJobOfferId = dbHandler.createJobOffer(job);
                    saveButton.setBackgroundColor(Color.GREEN);

                    Intent intent = new Intent(AddJobOfferActivity.this, JobSavedActivity.class);
                    intent.putExtra("thisJobOfferId", thisJobOfferId);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(AddJobOfferActivity.this,"All fields need to be completed.",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonCancelJobOffer:
                onBackPressed();
                break;
            case R.id.returnHome:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}