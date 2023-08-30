package edu.gatech.seclass.jobcompare6300.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.JobComparisonActivity;
import edu.gatech.seclass.jobcompare6300.MainActivity;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.model.SystemMgr;

public class JobRankingActivity extends AppCompatActivity {
    private SystemMgr dbHandler = new SystemMgr(JobRankingActivity.this);
    private ArrayList<Job> jobList;
    ListView lv_jobList;
    ArrayAdapter<String> jobAdapter;
    List<Long> jobIdList = new ArrayList<Long>();
    List<Long> selectedPosition = new ArrayList<Long>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_ranking);

        lv_jobList = (ListView) findViewById(R.id.job_list);
        jobList = dbHandler.listJobs();
        String[] jobArray = new String[jobList.size()];

        for (int i = 0; i < jobList.size(); i++) {
            Job job = jobList.get(i);
            String currJob = job.isCurrentJob() ? "(Current Job)" : "";
            jobArray[i] = job.getCompany() + " - " + job.getTitle() + ' '+currJob;
            jobIdList.add((long)job.getJobID());
        }
        jobAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,jobArray);
        lv_jobList.setAdapter(jobAdapter);

        lv_jobList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv_jobList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                long selected = lv_jobList.getItemIdAtPosition(i);
                //System.out.println(selected);
                //System.out.println(selectedPosition.toString());
                lv_jobList.setItemChecked(i,true);
                if(selectedPosition.contains(selected)){
                    selectedPosition.remove(selected);
                    lv_jobList.setItemChecked(i,false);
                } else if (selectedPosition.size() < 2) {
                    selectedPosition.add(selected);
                    lv_jobList.setItemChecked(i,true);
                } else {
                    Toast.makeText(JobRankingActivity.this,"User can only select two jobs to compare.",Toast.LENGTH_SHORT).show();
                    lv_jobList.setItemChecked(i,false);
                }
            }
        });

    }

    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCompare:
                if (selectedPosition.isEmpty() || selectedPosition.size() < 2){
                    Toast.makeText(this,"Please select two jobs to compare.",Toast.LENGTH_SHORT).show();
                }
                else {
                    // add jobID
                    Intent intent = new Intent(this, JobComparisonActivity.class);
                    Bundle extras = new Bundle();
                    int i = 0;
                    for (long position:selectedPosition){
                        extras.putLong("comparisonJobID"+Integer.toString(i+1), jobIdList.get((int)position));
                        i+=1;
                    }
                    intent.putExtras(extras);startActivity(intent);
                    startActivity(intent);
                }
                break;
            case R.id.buttonCancel:
                onBackPressed();
                break;
            case R.id.returnHome:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}