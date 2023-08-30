# Assignment 5 - Requirements

1. When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet).  

- **_For this "entry point" requirement, I created a very basic class that's sole job is to create/load the main menu and has a single attribute (it's title).  The MainMenu class has methods to enterCurrentJobDetails(), enterJobOfferDetails(), adjustComparisonSettings(), listAndRankJobOffers().  There is a boolean flag jobOfferExists that can be used to disable "compare job offers".  User was not depicted here since it was stated in Office Hours/Ed Discussion that this was a single user system and thought it would be redundant/unnecessary._**

2. When choosing to enter current job details, a user will:
   - Be shown a user interface to enter (if it is the first time) or edit all the details of their current job, which consist of:
     - Title
     - Company
     - Location (entered as city and state)
     - Cost of living in the location (expressed as an index)
     - Yearly salary
     - Yearly bonus
     - Restricted Stock Unit Award (expressed as a lump sum vested over 4 years)
     - Relocation stipend (A single value from $0 to $25,000)
     - Personal Choice Holidays (A single overall number of days from 0 to 20)
   - Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

- **_For this requirement, I created a Job class that contains a boolean that can be used to flag this object as the current job or not.  The rest of the attributes on this class pertain to the specific requirement data (Title, Company, etc.).  I included two methods saveJobDetails() and cancelJobChanges() that satisfy the two action requirements._**

3. When choosing to enter job offers, a user will:
   Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.
   Be able to either save the job offer details or cancel.
   Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).

- **_For this requirement, I added methods returnToMainMenu(), addJobOffer() which will instantiate a new Job object, and compareJobOffer() which will compare the current new job offer with the current job._**

4. When adjusting the comparison settings, the user can assign integer weights to:
   - Yearly salary
   - Yearly bonus
   - Restricted Stock Unit Award
   - Relocation stipend
   - Personal Choice Holidays 

   If no weights are assigned, all factors are considered equal.

- **_For this requirement, I created a ComparisonSettings class, with a weight attribute for yearly salary, yearly bonus, RSUs, relocation stipend, and personal choice holidays and initialized each to 1.    I also added a method to save changes and to reset back to default settings (all back to 1)._**

5. When choosing to compare job offers, a user will:
   - Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.
   - Select two jobs to compare and trigger the comparison.
   - Be shown a table comparing the two jobs, displaying, for each job:
     - Title
     - Company
     - Location
     - Yearly salary adjusted for cost of living
     - Yearly bonus adjusted for cost of living
     - Restricted Stock Unit Award
     - Relocation stipend
     - Personal Choice Holidays

   - Be offered to perform another comparison or go back to the main menu.

- **_For this requirement, I added methods to the MainMenu class: listAndRankJobOffers() and compareJobOffers(Job, Job).  The first will be used to generate a ranked list from best to worst and the second will take two Job objects as parameters and display the listed data._**
   

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
- **_This requirement is an implementation detail of the listAndRankJobOffers() and not depicted in the UML diagram_**

7. The user interface must be intuitive and responsive.

- **_This requirement is a GUI implementation and technology selection detail and not depicted in the UML diagram_**

8. For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).

- **_This requirement is an implementation detail and not depicted in the UML diagram_**