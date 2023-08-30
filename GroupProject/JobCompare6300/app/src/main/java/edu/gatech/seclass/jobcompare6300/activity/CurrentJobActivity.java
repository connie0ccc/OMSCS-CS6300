package edu.gatech.seclass.jobcompare6300.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.model.SystemMgr;

public class CurrentJobActivity extends AppCompatActivity {

    private EditText jobTitle;
    private EditText companyName;
    private EditText city;
    private EditText state;
    private EditText costOfLivingIndex;
    private EditText yearlySalary;
    private EditText yearlyBonus;
    private EditText yearlyStock;
    private EditText relocationStipend;
    private EditText yearlyHolidays;

    private Button saveButton;
    private Button cancelButton;

    SystemMgr dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_job);

        jobTitle = (EditText) findViewById(R.id.jobTitle);
        companyName = (EditText) findViewById(R.id.companyName);
        city = (EditText) findViewById(R.id.cityLocation);
        state = (EditText) findViewById(R.id.state);
        costOfLivingIndex = (EditText) findViewById(R.id.costOfLiving);
        yearlySalary = (EditText) findViewById(R.id.yearlySalary);
        yearlyBonus = (EditText) findViewById(R.id.yearlyBonus);
        yearlyStock = (EditText) findViewById(R.id.RSU);
        relocationStipend = (EditText) findViewById(R.id.relocationStipend);
        yearlyHolidays = (EditText) findViewById(R.id.personalDays);
        saveButton = (Button) findViewById(R.id.buttonSave);
        cancelButton = (Button) findViewById(R.id.buttonCancelCurrentJob);

        dbHandler = new SystemMgr(CurrentJobActivity.this);
        Job currentJob = dbHandler.getCurrentJob();
        if (currentJob != null) {
            jobTitle.setText(currentJob.getTitle());
            companyName.setText(currentJob.getCompany());
            city.setText(currentJob.getCity());
            state.setText(currentJob.getState());
            costOfLivingIndex.setText(Integer.toString(currentJob.getCostOfLivingIndex()));
            yearlySalary.setText(Float.toString(currentJob.getYearlySalary()));
            yearlyBonus.setText(Float.toString(currentJob.getYearlyBonus()));
            yearlyStock.setText(Float.toString(currentJob.getStockAward()));
            relocationStipend.setText(Float.toString(currentJob.getRelocationStipend()));
            yearlyHolidays.setText(Integer.toString(currentJob.getHolidays()));
        }

    }

    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSave:
                boolean error = false;
                saveButton.setBackgroundColor(Color.GRAY);

                String jobTitleString = jobTitle.getText().toString();
                String companyNameString = companyName.getText().toString();
                String cityString = city.getText().toString();
                String stateString = state.getText().toString();
                String costOfLivingIndexString = costOfLivingIndex.getText().toString();
                String yearlySalaryString = yearlySalary.getText().toString();
                String yearlyBonusString = yearlyBonus.getText().toString();
                String stockAwardString = yearlyStock.getText().toString();
                String relocationStipendString = relocationStipend.getText().toString();
                String yearlyHolidaysString = yearlyHolidays.getText().toString();

                if(jobTitleString.trim().length() == 0 || companyNameString.trim().length() == 0 || cityString.trim().length() == 0 || stateString.trim().length() == 0 ||
                        costOfLivingIndexString.trim().length() == 0 || yearlySalaryString.trim().length() == 0 || yearlyBonusString.trim().length() == 0 ||
                        stockAwardString.trim().length() == 0 || relocationStipendString.trim().length() == 0 || yearlyHolidaysString.trim().length() == 0) {
                    {
                        //saveButton.setError("All fields required.");
                        Toast.makeText(CurrentJobActivity.this,"All fields need to be completed.",Toast.LENGTH_SHORT).show();
                        error = true;
                    }
                } else if(Float.parseFloat(relocationStipendString) < 0 || Float.parseFloat(relocationStipendString) > 25000) {
                    Toast.makeText(CurrentJobActivity.this,"Relocation Stipend should be in the range 0 to 25000.",Toast.LENGTH_SHORT).show();
                    error = true;
                } else if(Integer.parseInt(yearlyHolidaysString) < 0 || Integer.parseInt(yearlyHolidaysString) > 20) {
                    Toast.makeText(CurrentJobActivity.this,"Personal Choice Holidays should be in the range 0 to 20.",Toast.LENGTH_SHORT).show();
                    error = true;
                } else if(Integer.parseInt(costOfLivingIndexString) <= 0) {
                    Toast.makeText(CurrentJobActivity.this,"Cost of living should be greater then 0.",Toast.LENGTH_SHORT).show();
                    error = true;
                }

                if(!error) {

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

                    if (dbHandler.getCurrentJob() != null) {
                        dbHandler.updateCurrentJob(job);
                        saveButton.setBackgroundColor(Color.GREEN);
                    } else {
                        dbHandler.createCurrentJob(job);
                        saveButton.setBackgroundColor(Color.GREEN);
                    }
                    onBackPressed();
                }
                break;
            case R.id.buttonCancelCurrentJob:
                onBackPressed();
                break;
            case R.id.returnHome:
                onBackPressed();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Utils.redirectToHomeActivity(this);
    }


}
