Requirements
1. When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet1).   
**Answer**: To realize this requirement, I added a 'Main' to the register class as an entry point. The startup() method will show the main menu to the user, with clickable buttons/links of (1) Enter/Edit current job (2) Enter job offers and (3) Adjust the comparison settings. And before that, a check will be run in the background to see if there are either at least two job offers, in case there is no current job, or at least one job offer, in case there is a current job. If so, a button/link of (4) Compare job offers as listed in the requirements will also be added. The user will be able to click and choose from the 4 options. If user selects (1) or (2), enterOrEditJob() method will be called. If user selects (3), updateSettings() method will be called. If user selects (4), compareJob() method will be called.
2. When choosing to enter current job details, a user will:
   a. Be shown a user interface to enter (if it is the first time) or edit all the details of their current job, which consist of:
   i. Title  
   ii. Company  
   iii. Location (entered as city and state)  
   iv. Cost of living in the location (expressed as an index)  
   v. Yearly salary  
   vi. Yearly bonus  
   vii. Restricted Stock Unit Award (expressed as a lump sum vested over 4 years)  
   viii. Relocation stipend (A single value from $0 to $25,000)  
   ix. Personal Choice Holidays (A single overall number of days from 0 to 20)    
   b. Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.  
**2.a. Answer**: To realize this requirement, after user select (1) on main menu, the program will run in the background to check if user has already entered a current job. If there is no current job entered yet, the addJob() method in Job class will be called, and user will be shown a user interface to enter the values of the attributes of Job class to create a new Job instance (with isCurrentJob attribute set as True). If there is already a current job, then the edit() method in CurrentJob will be called, and the user will be shown the exiting information of the current job entered, and update the values as needed and save it.  
   **2.b. Answer**: To realize this requirement, a "Save" button and a "Cancel" button will be shown on the user interface for user to either save the job offer details or cancel. After user clicking on one of the buttons, user will be shown the main menu by the startup() method in Main class.
3. When choosing to enter job offers, a user will:  
   a. Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.  
   b. Be able to either save the job offer details or cancel.  
   c. Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).  
   **3.a Answer**: To realize this requirement, after user select (2) on main menu, the addJob() method in Job class will be called, and user will be shown a user interface to enter the values of the attributes of Job class to create a new Job instance (with isCurrentJob attribute set as False).  
   **3.b Answer**: To realize this requirement, a "Save" button and a "Cancel" button will be shown on the user interface for user to either save the job offer details and create a job offer instance or cancel.  
   **3.c Answer**: To realize this requirement, after the user saves or cancels entering a new job offer, user will be shown a user interface with a "Enter another offer" button and a "Return to the main menu" button. Also, a check will run in background to see if user has saved the offer and if the current job exists. If so, a button of "Compare with current job" button will also be shown. If user clicks the "Enter another offer" button, the addJob() method in Job class will be called to add a new job offer. If user clicks the "Return to the main menu" button, user will be shown the main menu by the startup() method in Main class. If user clicks the "Compare with current job" button, the compareJob() method of this job offer will be called with current job passed in as the argument.
4. When adjusting the comparison settings, the user can assign integer weights to:  
   a. Yearly salary  
   b. Yearly bonus  
   c. Restricted Stock Unit Award  
   d. Relocation stipend  
   e. Personal Choice Holidays  
   If no weights are assigned, all factors are considered equal.  
   **Answer**: To realize this requirement, after user select (3) on main menu, user will be shown all the current values of the 5 weights. If user has never adjusted the comparison settings before, all values will be set default to 1. After user entered new values for the weights, the updateComparisonSettings() method in ComparisonSettings class will be called to update and save these values.  
5. When choosing to compare job offers, a user will:  
   a. Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.  
   b. Select two jobs to compare and trigger the comparison.  
   c. Be shown a table comparing the two jobs, displaying, for each job:  
   i. Title  
   ii. Company  
   iii. Location  
   iv. Yearly salary adjusted for cost of living  
   v. Yearly bonus adjusted for cost of living  
   vi. Restricted Stock Unit Award  
   vii. Relocation stipend  
   viii. Personal Choice Holidays  
   d. Be offered to perform another comparison or go back to the main menu.  
   **5.a. Answer**: To realize this requirement, after user select (4) on main menu, for the current job and all job offer instance, the computeJobScore() method will be called to use the current weights values in the ComparisonSettings class to calculate a score. Then, user will be shown a list of all the jobs (including current job and all job offers) with their title, company and isCurrentJob attributes as well as the score computed. The list will be ordered by the score computed in descending order.  
   **5.b. Answer**: To realize this requirement, on the interface shown in 5.a, a checkbox will be added to each job, and a button of "Compare two jobs" will also be added. User will be able to select only two jobs from the list and click the button to trigger the comparison.  
   **5.c. Answer**: To realize this requirement, after user clicking the button in 5.b, the compareJob() method of one of jobs selected will be called with another job passed in as the argument, and the method will retrieve the attributes of both jobs (with yearlySalary and yearlyBonus adjusted for costOfLiving index) and display these attributes in a table to the user.  
   **5.d. Answer**: To realize this requirement, on the user interface in 5.c, a "Perform another comparison" button and a "Return to main menu" button will be added. If user clicks the "Perform another comparison" button, compareJob() method in the Main class will be called and user can start a new comparison. If user clicks "Return to main menu" button, user will be shown the main menu by the startup() method in Main class.
6. When ranking jobs, a job’s score is computed as the weighted sum of:  
   AYS + AYB + (RSUA / 4) + RELO + (PCH * AYS / 260)  
   where:  
   AYS = yearly salary adjusted for cost of living  
   AYB = yearly bonus adjusted for cost of living  
   RSU = restricted stock unit award  
   RELO = relocation stipend  
   PCH = personal choice holidays  
   For example, if the weights are 2 for the yearly salary, 2 for relocation stipend and 1 for all other factors, the score would be computed as:  
   2/7 * AYS + 1/7 * AYB + 1/7 * (RSUA / 4) + 2/7 * RELO + 1/7 * (PCH * AYS / 260)  
   **Answer**: To realize this requirement, I have added a computeJobScore(ComparisonSettings) method in the Job class. This method will use the weights in the ComparisonSettings class to compute the score for each job based on the formula in the requirement.   
7. The user interface must be intuitive and responsive.  
   **Answer**: This is not represented in my design, as it will be handled entirely within the GUI implementation.  
8. For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).  
   **Answer**: Since there is a single system, I do not set unique ids to distinguish different systems.