package edu.gatech.seclass.sdpencryptor;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * This is a Georgia Tech provided code example for use in assigned private GT repositories. Students and other users of this template
 * code are advised not to share it with other students or to make it available on publicly viewable websites including
 * repositories such as github and gitlab.  Such sharing may be investigated as a GT honor code violation. Created for CS6300.
 */


@RunWith(AndroidJUnit4.class)
public class SmallTestExamples {

    @Rule
    public ActivityScenarioRule<MainActivity> tActivityRule = new ActivityScenarioRule<>(
            MainActivity.class);

    private void replaceTextHelper(int viewId, String stringToBeSet) {
        // to reduce flaky test, https://stackoverflow.com/a/53430379/1326678
        onView(withId(viewId)).perform(clearText(), replaceText(stringToBeSet), closeSoftKeyboard());
    }

    @Test
    public void Screenshot1() {
        replaceTextHelper(R.id.entryTextID, "Cat & Dog");
        replaceTextHelper(R.id.argInput1ID, "5");
        replaceTextHelper(R.id.argInput2ID, "8");
        onView(withId(R.id.encryptButtonID)).perform(click());
        onView(withId(R.id.textEncryptedID)).check(matches(withText("yiP & DqM")));
    }

    @Test
    public void Screenshot2() {
        replaceTextHelper(R.id.entryTextID, "Up with the White And Gold!");
        replaceTextHelper(R.id.argInput1ID, "1");
        replaceTextHelper(R.id.argInput2ID, "1");
        onView(withId(R.id.encryptButtonID)).perform(click());
        onView(withId(R.id.textEncryptedID)).check(matches(withText("Vq xjui uif Xijuf Boe Hpme!")));
    }

    @Test
    public void Screenshot3() {
        replaceTextHelper(R.id.entryTextID, "abcdefg");
        replaceTextHelper(R.id.argInput1ID, "5");
        replaceTextHelper(R.id.argInput2ID, "1");
        onView(withId(R.id.encryptButtonID)).perform(click());
        onView(withId(R.id.textEncryptedID)).check(matches(withText("bglqvAF")));
    }

    @Test
    public void trigger() {
        replaceTextHelper(R.id.entryTextID, "__trigger__");
        replaceTextHelper(R.id.argInput1ID, "5");
        replaceTextHelper(R.id.argInput2ID, "1");
        onView(withId(R.id.encryptButtonID)).perform(click());
        onView(withId(R.id.textEncryptedID)).check(matches(withText("__IyPFFvy__")));
    }

    @Test
    public void errorTest1() {
        replaceTextHelper(R.id.entryTextID, "");
        replaceTextHelper(R.id.argInput1ID, "0");
        replaceTextHelper(R.id.argInput2ID, "0");
        onView(withId(R.id.encryptButtonID)).perform(click());
        onView(withId(R.id.entryTextID)).check(matches(hasErrorText("Invalid Entry Text")));
        onView(withId(R.id.argInput1ID)).check(matches(hasErrorText("Invalid Arg Input 1")));
        onView(withId(R.id.argInput2ID)).check(matches(hasErrorText("Invalid Arg Input 2")));
        onView(withId(R.id.textEncryptedID)).check(matches(withText("")));
    }

    @Test
    public void gradingTest13() {
        replaceTextHelper(R.id.entryTextID, "Panda Cat");
        replaceTextHelper(R.id.argInput1ID, "23");
        replaceTextHelper(R.id.argInput2ID, "1");
        onView(withId(R.id.encryptButtonID)).perform(click());
        onView(withId(R.id.textEncryptedID)).check(matches(withText("ob0ib zbe")));
    }
}
