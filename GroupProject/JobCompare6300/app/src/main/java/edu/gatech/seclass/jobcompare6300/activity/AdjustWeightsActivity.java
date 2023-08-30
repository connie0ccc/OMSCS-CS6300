package edu.gatech.seclass.jobcompare6300.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.model.ComparisonSettings;
import edu.gatech.seclass.jobcompare6300.model.SystemMgr;

public class AdjustWeightsActivity extends AppCompatActivity {
    private EditText yearlySalaryWeight;
    private EditText yearlyBonusWeight;
    private EditText RSUWeight;
    private EditText relocationStipendWeight;
    private EditText personalDaysWeight;

    private Button saveButton;
    private Button cancelButton;
    private SystemMgr dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adjust_weights);

        yearlySalaryWeight = (EditText) findViewById(R.id.yearlySalaryWeight);
        yearlyBonusWeight = (EditText) findViewById(R.id.yearlyBonusWeight);
        RSUWeight = (EditText) findViewById(R.id.RSUWeight);
        relocationStipendWeight = (EditText) findViewById(R.id.relocationStipendWeight);
        personalDaysWeight = (EditText) findViewById(R.id.personalDaysWeight);

        dbHandler = new SystemMgr(AdjustWeightsActivity.this);
        ComparisonSettings settings = dbHandler.getComparisonSettings();

        yearlySalaryWeight.setText(Integer.toString(settings.getYearlySalaryWeight()));
        yearlyBonusWeight.setText(Integer.toString(settings.getYearlyBonusWeight()));
        RSUWeight.setText(Integer.toString(settings.getStockAwardWeight()));
        relocationStipendWeight.setText(Integer.toString(settings.getRelocationStipendWeight()));
        personalDaysWeight.setText(Integer.toString(settings.getHolidaysWeight()));

        saveButton = (Button) findViewById(R.id.buttonSaveSetting);
        cancelButton = (Button) findViewById(R.id.buttonCancelSetting);
    }

    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSaveSetting:


                String yearlySalaryWeightString = yearlySalaryWeight.getText().toString();
                String yearlyBonusWeightString = yearlyBonusWeight.getText().toString();
                String RSUWeightString = RSUWeight.getText().toString();
                String relocationStipendWeightString = relocationStipendWeight.getText().toString();
                String personalDaysWeightString = personalDaysWeight.getText().toString();


                if(yearlySalaryWeightString.trim().length() == 0 || yearlyBonusWeightString.trim().length() == 0 ||
                        RSUWeightString.trim().length() == 0 || relocationStipendWeightString.trim().length() == 0 ||
                        personalDaysWeightString.trim().length() == 0 ) {
                    Toast.makeText(AdjustWeightsActivity.this,"All weights must be entered.",Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(yearlySalaryWeight.getText().toString()) == 0 && Integer.parseInt(yearlyBonusWeight.getText().toString()) == 0 &&
                        Integer.parseInt(RSUWeight.getText().toString()) == 0 && Integer.parseInt(relocationStipendWeight.getText().toString()) == 0  &&
                        Integer.parseInt(personalDaysWeight.getText().toString()) == 0) {
                    Toast.makeText(AdjustWeightsActivity.this,"You must have at least 1 weight greater than 0.",Toast.LENGTH_SHORT).show();
                } else {
                    ComparisonSettings settings = new ComparisonSettings();
                    settings.setYearlySalaryWeight(Integer.parseInt(yearlySalaryWeight.getText().toString()));
                    settings.setYearlyBonusWeight(Integer.parseInt(yearlyBonusWeight.getText().toString()));
                    settings.setStockAwardWeight(Integer.parseInt(RSUWeight.getText().toString()));
                    settings.setRelocationStipendWeight(Integer.parseInt(relocationStipendWeight.getText().toString()));
                    settings.setHolidaysWeight(Integer.parseInt(personalDaysWeight.getText().toString()));

                    dbHandler.updateComparisonSettings(settings);
                    saveButton.setBackgroundColor(Color.GREEN);
                }

                break;
            case R.id.buttonCancelSetting:
                onBackPressed();
                break;
            case R.id.returnHome:
                onBackPressed();
                break;
        }
    }
}