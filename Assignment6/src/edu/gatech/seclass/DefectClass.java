package edu.gatech.seclass;

/**
 * This is a Georgia Tech provided code example for use in assigned
 * private GT repositories. Students and other users of this template
 * code are advised not to share it with other students or to make it
 * available on publicly viewable websites including repositories such
 * as GitHub and GitLab.  Such sharing may be investigated as a GT
 * honor code violation. Created for CS6300 Spring 2023.
 *
 * Template provided for the White-Box Testing Assignment. Follow the
 * assignment directions to either implement or provide comments for
 * the appropriate methods.
 */

public class DefectClass {

    public static void exampleMethod1(int a) {
        // Division by 0 fault occurs when any number is devided by 0.
        int x = a / 0; // Example of instruction that makes the method
                       // fail with an ArithmeticException
    }

    public static int exampleMethod2(int a, int b) {
        // Division by 0 fault occurs when any number is devided by 0.
        return (a + b) / 0; // Example of instruction that makes the
                            // method fail with an ArithmeticException
    }

    public static void exampleMethod3() {
        // NOT POSSIBLE: This method cannot be implemented because
        // <REPLACE WITH REASON> (this is the example format for a
        // method that is not possible) ***/
    }
    //  Task 1: ask 1: Create in class DefectClass a method called defectMethod1 that contains a division by zero fault and at least two branches, such that
    //  (1) it is possible to create a test suite that achieves less than 100% branch coverage and reveals the fault, and
    //  (2) it is possible to create a test suite that achieves 100% branch coverage and does not reveal the fault.

    public static int defectMethod1(int a, int b) {
        //Division by 0 fault occurs when a and b are additive inverses of each other
        int result=a/(a+b);
        if (a == -b) {
            System.out.println("a and b are additive inverses!");
            return result;
        } else {
            System.out.println("a is non-positive!");
            return result;
        }
    }

    public static void defectMethod2() {
        // NOT POSSIBLE: This method cannot be implemented because
        // To achieve 100% path coverage, we need to execute all possible paths in the method, including those that do not result in a division by zero error.
        // This means that we would need to execute both the true and false branches of the conditional statement in the method.
        // However, if we execute only the true branch of the conditional statement, we will not expose the division by zero error, and if we execute only the false branch, the method will not execute to completion, and we will not achieve 100% path coverage.
    }

    public static boolean defectMethod3(boolean a, boolean b) {
        int m = 1;
        int n = -2;
        if(a) {
            m = 2*n;
        }else{
            m = 2*m;}
        if(b){
            n = 4-m;}
        return ((100/(m+n))<= 0);
    }

    public static int defectMethod4(boolean a, int b, int c, int d) {
        int result = 0; 
        if (a) {
            result = 1; 
        } else { 
            if ((b == 0) || ((c > 0) && (d != 0))) { 
                result = 2; 
            } else { 
                result = 3; 
            } 
        } 
        return result; 
    }
    

    public static String[] defectMethod5() {
        String a[] = new String[7];
        /*
        public static boolean defectMethod5(boolean a, boolean b) {
          int m = 1;
          int n = -7;
          if(a)
            m = n; 
          else
            m = 3*m;
          if(b)
            n = 2-m;
          return ((100/(m-n))<= 0);
        }

        */
        //
        // Replace the "?" in column "output" with "T", "F", or "E":
        //
        //         | a | b |output|
        //         ================
        a[0] =  /* | T | T | <T, F, or E> (e.g., "T") */ "T";
        a[1] =  /* | T | F | <T, F, or E> (e.g., "T") */ "E";
        a[2] =  /* | F | T | <T, F, or E> (e.g., "T") */ "F";
        a[3] =  /* | F | F | <T, F, or E> (e.g., "T") */ "F";
        // ================
        //
        // Replace the "?" in the following sentences with "NEVER",
        // "SOMETIMES" or "ALWAYS":
        //
        a[4] = /* Test suites with 100% path coverage */ "ALWAYS";
               /*reveal the fault in this method.*/
        a[5] = /* Test suites with 100% branch coverage */ "SOMETIMES";
               /*reveal the fault in this method.*/
        a[6] =  /* Test suites with 100% statement coverage */ "SOMETIMES";
                /*reveal the fault in this method.*/
        // ================
        return a;
    }
}

