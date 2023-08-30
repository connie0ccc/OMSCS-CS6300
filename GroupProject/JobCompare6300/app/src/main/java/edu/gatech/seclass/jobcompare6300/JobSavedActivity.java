package edu.gatech.seclass.jobcompare6300;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.seclass.jobcompare6300.activity.AddJobOfferActivity;
import edu.gatech.seclass.jobcompare6300.model.SystemMgr;

public class JobSavedActivity extends AppCompatActivity {
    private Button addAnotherJobButton;
    private Button compareWithCurrentButton;

    private Button homeButton;
    private SystemMgr dbHandler = new SystemMgr(JobSavedActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_saved);

        addAnotherJobButton = (Button) findViewById(R.id.buttonAddAnotherJob);
        compareWithCurrentButton = (Button) findViewById(R.id.buttonCompareWithCurrent);
        homeButton = (Button) findViewById(R.id.returnHome);
    }
    public void handleClick(View view) {
        switch(view.getId()) {
            case R.id.buttonAddAnotherJob:
                startActivity(new Intent(this, AddJobOfferActivity.class));
                break;
            case R.id.buttonCompareWithCurrent:
                if (dbHandler.getCurrentJob() == null){
                    Toast.makeText(this,"No current job entered.",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = getIntent();
                    long thisJobOfferId = intent.getLongExtra("thisJobOfferId",0);

                    intent = new Intent(JobSavedActivity.this, CurrentOfferComparisonActivity.class);
                    intent.putExtra("thisJobOfferId", thisJobOfferId);
                    startActivity(intent);
                }
                break;
            case R.id.returnHome:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        }
}
