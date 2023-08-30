package edu.gatech.seclass;

import org.junit.jupiter.api.Test;

/**
 * This is a Georgia Tech provided code example for use in assigned
 * private GT repositories. Students and other users of this template
 * code are advised not to share it with other students or to make it
 * available on publicly viewable websites including repositories such
 * as GitHub and GitLab.  Such sharing may be investigated as a GT
 * honor code violation. Created for CS6300 Spring 2023.
 *
 * Junit test class provided for the White-Box Testing Assignment.
 * This class should not be altered.  Follow the directions to create
 * similar test classes when required.
 */

public class DefectClassTestMCDC4 {

    @Test
    public void Test1() { DefectClass.defectMethod4(true,0,0,0); }

    @Test
    public void Test2() { DefectClass.defectMethod4(false,0,0,1); }

    @Test
    public void Test3() { DefectClass.defectMethod4(false,1,0,1); }
    @Test
    public void Test4() { DefectClass.defectMethod4(false,1,1,1); }

    @Test
    public void Test5() { DefectClass.defectMethod4(false,1,1,0); }
}
