package edu.gatech.seclass.jobcompare6300;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.model.ComparisonSettings;
import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.model.SystemMgr;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class JobCompareInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("edu.gatech.seclass.jobcompare6300", appContext.getPackageName());
    }

    private SystemMgr sysMgrObj;

//    @BeforeClass
//    public static void deleteDB() {
//        ApplicationProvider.getApplicationContext().deleteDatabase("jobcomparedb");
//    }


    @Before
    public void setUp() {
        ApplicationProvider.getApplicationContext().deleteDatabase("jobcomparedb");
        sysMgrObj = new SystemMgr(ApplicationProvider.getApplicationContext());
    }

    @After
    public void tearDown() {
        ApplicationProvider.getApplicationContext().deleteDatabase("jobcomparedb");
        sysMgrObj.close();
    }

    /*  I've created subsections to add tests for each of our functions in SystemMgr */

    //createCurrentJob() tests
    @Test
    public void createCurrentJobTest1() {

        Job job = new Job();
        //CurrentJobActivity currentJobActivity = new CurrentJobActivity();
        //sysMgrObj = new SystemMgr(RuntimeEnvironment.application);

        job.setTitle("Software Engineer II");
        job.setCompany("Microsoft");
        job.setCity("Reston");
        job.setState("Virginia");
        job.setCostOfLivingIndex(223);
        job.setYearlySalary(100001);
        job.setYearlyBonus(20001);
        job.setRelocationStipend(2001);
        job.setStockAward(23001);
        job.setHolidays(3);

        long jobID = sysMgrObj.createCurrentJob(job);
        Job currentJob = sysMgrObj.getCurrentJob();
        long currentJobID = currentJob.getJobID();

        System.out.println("createCurrentJobTest1 JobID = " + jobID);
        System.out.println("createCurrentJobTest1 currentJobID = " + currentJobID);


        assertEquals(true, jobID != -1);
        assertEquals(true, jobID == currentJobID);
    }


    //updateCurrentJob() tests
    @Test
    public void updateCurrentJobTest1() {
        Job job1 = new Job();
        job1.setTitle("DA");
        job1.setCompany("Company1");
        job1.setCity("City1");
        job1.setState("State1");
        job1.setCostOfLivingIndex(110);
        job1.setYearlySalary(10000);
        job1.setYearlyBonus(10000);
        job1.setStockAward(10000);
        job1.setRelocationStipend(1000);
        job1.setHolidays(10);
        sysMgrObj.createCurrentJob(job1);

        Job job = new Job();
        job.setTitle("Software Engineer II");
        job.setCompany("Microsoft");
        job.setCity("Reston");
        job.setState("Virginia");
        job.setCostOfLivingIndex(223);
        job.setYearlySalary(100001);
        job.setYearlyBonus(20001);
        job.setRelocationStipend(2001);
        job.setStockAward(23001);
        job.setHolidays(3);

        long count = sysMgrObj.updateCurrentJob(job);

        System.out.println("updateCurrentJobTest1 count = " + count);

        assertEquals(true, count == 1);
    }

    //createJobOffer() tests

    //First test checks if successful row creation
    @Test
    public void createJobOfferTest1() {
        Job job = new Job();

        job.setTitle("Senior Program Manager");
        job.setCompany("Google");
        job.setCity("Mountain View");
        job.setState("California");
        job.setCostOfLivingIndex(211);
        job.setYearlySalary(100001);
        job.setYearlyBonus(20001);
        job.setRelocationStipend(2001);
        job.setStockAward(23001);
        job.setHolidays(3);

        long jobID = sysMgrObj.createJobOffer(job);

        System.out.println("createJobOfferTest 1 JobID = " + jobID);

        assertEquals(true, jobID != -1);

    }

    //Second test checks that job offer created is not flagged as current job
    @Test
    public void createJobOfferTest2() {
        Job job = new Job();

        job.setTitle("Senior Program Manager");
        job.setCompany("Google");
        job.setCity("Mountain View");
        job.setState("California");
        job.setCostOfLivingIndex(211);
        job.setYearlySalary(100001);
        job.setYearlyBonus(20001);
        job.setRelocationStipend(2001);
        job.setStockAward(23001);
        job.setHolidays(3);

        long jobID = sysMgrObj.createJobOffer(job);

        Job currentJob = sysMgrObj.getCurrentJob();
        long currentJobID = 0;
        if (currentJob != null) {
            currentJobID = currentJob.getJobID();
        }
        System.out.println("createJobOfferTest2 JobID = " + jobID);
        System.out.println("createJobOfferTest2 CurrentJobID = " + currentJobID);

        assertEquals(true, jobID != currentJobID);

    }
    //getComparisonSettings() tests; check if all current weights are 1.
    @Test
    public void getComparisonSettingsTest1() {
        ComparisonSettings savedSettings = sysMgrObj.getComparisonSettings();
        assertEquals(1, savedSettings.getYearlySalaryWeight());
        assertEquals(1, savedSettings.getYearlyBonusWeight());
        assertEquals(1, savedSettings.getStockAwardWeight());
        assertEquals(1, savedSettings.getRelocationStipendWeight());
        assertEquals(1, savedSettings.getHolidaysWeight());
    }
    //updateComparisonSettings() tests; try updating weights to 2,3,4,5,6
    @Test
    public void updateComparisonSettingsTest1() {
        ComparisonSettings compSettings = new ComparisonSettings();
        compSettings.setYearlySalaryWeight(2);
        compSettings.setYearlyBonusWeight(3);
        compSettings.setStockAwardWeight(4);
        compSettings.setRelocationStipendWeight(5);
        compSettings.setHolidaysWeight(6);
        sysMgrObj.updateComparisonSettings(compSettings);
        ComparisonSettings savedSettings = sysMgrObj.getComparisonSettings();
        assertEquals(2, savedSettings.getYearlySalaryWeight());
        assertEquals(3, savedSettings.getYearlyBonusWeight());
        assertEquals(4, savedSettings.getStockAwardWeight());
        assertEquals(5, savedSettings.getRelocationStipendWeight());
        assertEquals(6, savedSettings.getHolidaysWeight());
    }

    //compareJobs() tests; return jobs in correct order
    @Test
    public void compareJobsTest1() {
        Job job1 = new Job();
        job1.setTitle("DA");
        job1.setCompany("Company1");
        job1.setCity("City1");
        job1.setState("State1");
        job1.setCostOfLivingIndex(110);
        job1.setYearlySalary(10000);
        job1.setYearlyBonus(10000);
        job1.setStockAward(10000);
        job1.setRelocationStipend(1000);
        job1.setHolidays(10);

        Job job2 = new Job();
        job2.setTitle("DS");
        job2.setCompany("Company2");
        job2.setCity("City2");
        job2.setState("State2");
        job2.setCostOfLivingIndex(120);
        job2.setYearlySalary(20000);
        job2.setYearlyBonus(20000);
        job2.setStockAward(20000);
        job2.setRelocationStipend(2000);
        job2.setHolidays(15);

        ArrayList<Job> compJobList = sysMgrObj.compareJobs(job1,job2);
        assertEquals(2,compJobList.size());
        assertEquals(job2.getTitle(),compJobList.get(0).getTitle());
        assertEquals(job1.getTitle(),compJobList.get(1).getTitle());
    }

    //listJobs() tests; create 3 jobs and check if returned a list of 3 and in correct order
    @Test
    public void listJobsTest1() {
        Job job1 = new Job();
        job1.setTitle("DA");
        job1.setCompany("Company1");
        job1.setCity("City1");
        job1.setState("State1");
        job1.setCostOfLivingIndex(110);
        job1.setYearlySalary(10000);
        job1.setYearlyBonus(10000);
        job1.setStockAward(10000);
        job1.setRelocationStipend(1000);
        job1.setHolidays(10);

        Job job2 = new Job();
        job2.setTitle("DS");
        job2.setCompany("Company2");
        job2.setCity("City2");
        job2.setState("State2");
        job2.setCostOfLivingIndex(120);
        job2.setYearlySalary(20000);
        job2.setYearlyBonus(20000);
        job2.setStockAward(20000);
        job2.setRelocationStipend(2000);
        job2.setHolidays(15);

        Job job3 = new Job();
        job3.setTitle("SDE");
        job3.setCompany("Company3");
        job3.setCity("City3");
        job3.setState("State3");
        job3.setCostOfLivingIndex(130);
        job3.setYearlySalary(30000);
        job3.setYearlyBonus(30000);
        job3.setStockAward(30000);
        job3.setRelocationStipend(3000);
        job3.setHolidays(20);

        long jobId1 = sysMgrObj.createJobOffer(job1);
        long jobId2 = sysMgrObj.createJobOffer(job2);
        long jobId3 = sysMgrObj.createJobOffer(job3);
        List<Job> jobList = sysMgrObj.listJobs();
        assertEquals(3, jobList.size());
        assertEquals(jobId3,jobList.get(0).getJobID());
        assertEquals(jobId2,jobList.get(1).getJobID());
        assertEquals(jobId1,jobList.get(2).getJobID());
    }

    //getCurrentJob() tests; first check if returns null when current job does not exist, then create a current job and check if returned values all match.
    @Test
    public void getCurrentJobTest1() {
        assertNull(sysMgrObj.getCurrentJob());
        Job job1 = new Job();
        job1.setTitle("DA");
        job1.setCompany("Company1");
        job1.setCity("City1");
        job1.setState("State1");
        job1.setCostOfLivingIndex(110);
        job1.setYearlySalary(10000);
        job1.setYearlyBonus(10000);
        job1.setStockAward(10000);
        job1.setRelocationStipend(1000);
        job1.setHolidays(10);
        sysMgrObj.createCurrentJob(job1);
        Job currJob = sysMgrObj.getCurrentJob();
        assertEquals("DA", currJob.getTitle());
        assertEquals("Company1", currJob.getCompany());
        assertEquals("City1", currJob.getCity());
        assertEquals("State1", currJob.getState());
        assertEquals(110, currJob.getCostOfLivingIndex());
        assertEquals(10000, currJob.getYearlySalary(),0.01d);
        assertEquals(10000, currJob.getYearlyBonus(),0.01d);
        assertEquals(10000, currJob.getStockAward(),0.01d);
        assertEquals(1000, currJob.getRelocationStipend(),0.01d);
        assertEquals(10, currJob.getHolidays());
        assertTrue(currJob.isCurrentJob());
    }

    //getJob() tests; create a job offer and check if returned values all match.
    @Test
    public void getJobTest1() {
        Job job2 = new Job();
        job2.setTitle("DS");
        job2.setCompany("Company2");
        job2.setCity("City2");
        job2.setState("State2");
        job2.setCostOfLivingIndex(120);
        job2.setYearlySalary(20000);
        job2.setYearlyBonus(20000);
        job2.setStockAward(20000);
        job2.setRelocationStipend(2000);
        job2.setHolidays(15);
        long jobId2 = sysMgrObj.createJobOffer(job2);
        Job newJob = sysMgrObj.getJob((int)jobId2);
        assertEquals("DS", newJob.getTitle());
        assertEquals("Company2", newJob.getCompany());
        assertEquals("City2", newJob.getCity());
        assertEquals("State2", newJob.getState());
        assertEquals(120, newJob.getCostOfLivingIndex());
        assertEquals(20000, newJob.getYearlySalary(),0.01d);
        assertEquals(20000, newJob.getYearlyBonus(),0.01d);
        assertEquals(20000, newJob.getStockAward(),0.01d);
        assertEquals(2000, newJob.getRelocationStipend(),0.01d);
        assertEquals(15, newJob.getHolidays());
        assertFalse(newJob.isCurrentJob());
    }

    //calcScore() tests; Add 3 job offer and check if their scores are calculated correctly.
    @Test
    public void calcScoreTest1() {
        Job job1 = new Job();
        job1.setTitle("DA");
        job1.setCompany("Company1");
        job1.setCity("City1");
        job1.setState("State1");
        job1.setCostOfLivingIndex(110);
        job1.setYearlySalary(10000);
        job1.setYearlyBonus(10000);
        job1.setStockAward(10000);
        job1.setRelocationStipend(1000);
        job1.setHolidays(10);

        Job job2 = new Job();
        job2.setTitle("DS");
        job2.setCompany("Company2");
        job2.setCity("City2");
        job2.setState("State2");
        job2.setCostOfLivingIndex(120);
        job2.setYearlySalary(20000);
        job2.setYearlyBonus(20000);
        job2.setStockAward(20000);
        job2.setRelocationStipend(2000);
        job2.setHolidays(15);

        Job job3 = new Job();
        job3.setTitle("SDE");
        job3.setCompany("Company3");
        job3.setCity("City3");
        job3.setState("State3");
        job3.setCostOfLivingIndex(130);
        job3.setYearlySalary(30000);
        job3.setYearlyBonus(30000);
        job3.setStockAward(30000);
        job3.setRelocationStipend(3000);
        job3.setHolidays(20);

        sysMgrObj.createJobOffer(job1);
        sysMgrObj.createJobOffer(job2);
        sysMgrObj.createJobOffer(job3);
        List<Job> jobList = sysMgrObj.listJobs();
        assertEquals(11685.798816568049,jobList.get(0).getJobScore(),0.001d);
        assertEquals(8258.97435897436,jobList.get(1).getJobScore(),0.001d);
        assertEquals(4406.293706293706,jobList.get(2).getJobScore(),0.001d);
    }
//    @AfterClass
//    public static void deleteDBAfter() {
//        ApplicationProvider.getApplicationContext().deleteDatabase("jobcomparedb");
//    }
}