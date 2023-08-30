package edu.gatech.seclass.jobcompare6300;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import edu.gatech.seclass.jobcompare6300.model.Job;
import edu.gatech.seclass.jobcompare6300.model.SystemMgr;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("edu.gatech.seclass.jobcompare6300", appContext.getPackageName());

    }

    private SystemMgr sysMgrObj;

    @Before
    public void setUp() {
        sysMgrObj = new SystemMgr(ApplicationProvider.getApplicationContext());
    }

    @After
    public void tearDown() {
        sysMgrObj.close();
    }

    /*  I've created subsections to add tests for each of our functions in SystemMgr */

    //createJob() tests
    @Test
    public void createJobTest1() {
        assertEquals(4, 2 + 2);
    }

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

        assertEquals(jobID != -1, true);
    }
}