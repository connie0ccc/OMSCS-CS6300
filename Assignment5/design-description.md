# **Design Description**
#### CS6300 Spring 2023
#### Design Description Document

This document captures the design description for Assignment5. I also include an UML Class diagram, please refer to design.pdf file saved in the same folder.
# 

**Reqiurements**

 1. > When the app is started, the user is presented with the main menu, which allows the user to (1) enter current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare
    job offers    (disabled if no job offers were entered yet).*

To realize this requirement, I will use the MainMenu Class as the entry point to the application. When the app is started, ShowMenu() method will display the main menu that allows the user to (1) enter current job details, (2) enter job offers, (3) adjust the comparison settings, and (4) compare job offers.

2.  > When choosing to enter current job details, a user will:
a. Be shown a user interface to enter (if it is the first time) or edit all of the details of their current job, which consist of:
    i. Title  
    ii. Company  
    iii. Location (entered as city and state)  
    iv. Overall cost of living in the location (expressed as an index)  
    v. Yearly salary  
    vi. Yearly bonus  
    vii.  Restricted Stock Unit Award (expressed as a lump sum vested over 4 years)
    viii. Relocation stipend (A single value from $0 to $25,000) 
    ix. Personal Choice Holidays (A single overall number of days from 0 to 20)

When the user selects the current job, GetJobDetail() method will check if there is a current job stored within the job class. The Job class has a Boolean attribute “isCurrentJob” that differentiates the current job from other job offers. If the CurrentJob record does not exist, the user will be asked to enter the details (e.g., title, salary) of their current job using the AddJobDetail() and SaveJobDetail() methods.*

>>b. Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.

If the CurrentJob record exists, the detail will be displayed, allowing the user either to edit them by using the EditJobDetail() and SaveJobDetail() methods or return to the main menu by calling the ShowMenu() method. For the current job, the attribute isCurrentJob will have a default value of “true”.

3. > When choosing to _enter job offer,_ a user will:
a. Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.
b. Be able to either save the job offer details or cancel.

When the user selects to enter job offers in the Main Menu, the user will be asked to enter the details (e.g., Title, salary) of their offer using the AddJobDetail() and SaveJobDetail() methods. For job offers, the attribute isCurrentJob will have a default value of “false”.

>>c. Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).

After entering Job offer details and saving them, the user will be provided with three options 1) user can keep entering the offer using the method described above. 2)User can also return to the main menu by calling the ShowMenu() method. 3) user can compare the offer with the current job details. CompareJob() method will be called. GetJobDetails() method will show the record with the attribute isCurrentJob of true.


4. > When _adjusting the comparison settings,_ the user can assign integer _weights_ to:
a. Yearly salary
b. Yearly bonus
c. Restricted Stock Unit Award
d. Relocation stipend
e. Personal Choice Holidays
If no weights are assigned, all factors are considered equal.

In the main menu, the user can also select to adjust _the comparison settings class._ If the user the weight attributes are assigned a default value of 1. All factors will be considered equal if the user does not adjust them.

5. > When choosing to _compare job offers,_ a user will:
a. Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.

In the main menu, when the user chooses to _compare job offers, the_ getJobDetails() method will be called. It will list all the jobs saved in the Job class and display the records based on the score attribute from highest to lowest. A job with the attribute isCurrentJob = true will be indicated as the current job.
>>b. Select two jobs to compare and trigger the comparison

All jobs will have a check box next to them. And user can select up to 2 jobs and choose to initiate a comparison. A CompareJobOffer() method will be called.


>>c. Be shown a table comparing the two jobs, displaying, for each job:
i. Title
ii.Company
iii.Location
iv.Yearly salary adjusted for cost of living
v.Yearly bonus adjusted for cost of living
vi.Restricted Stock Unit Award
vii.Relocation stipend
viii.Personal Choice Holidays

The CompareJobOffer() method will display the above job attributes relating to the jobs selected for comparison

>>d. Be offered to perform another comparison or go back to the main menu.

User will be provided with the option for the perform another comparison by calling the CompareJobOffer() method or go back to the main menu by calling the ShowMenu() method.

6. > When ranking jobs, a job’s score is computed as the weighted sum of:
AYS + AYB + (RSUA / 4) + RELO + (PCH * AYS / 260)
where:
AYS = yearly salary adjusted for cost of living
AYB = yearly bonus adjusted for cost of living
RSU = restricted stock unit award
RELO = relocation stipend
PCH = personal choice holidays
For example, if the weights are 2 for the yearly salary, 2 for relocation stipend and 1 for all other factors, the score would be computed as:
2/7 * AYS + 1/7 * AYB + 1/7 * (RSUA / 4) + 2/7 * RELO + 1/7 * (PCH * AYS / 260)

CalculateJobScore() will be triggered every time the user adds/edits a record within the Job class, UpdateJobScore() will be triggered every time the user updates the ComparisonSetting class, and all score saved in within the Job class will be updated accordingly.

7. > The user interface must be intuitive and responsive.

This is not represented in this design, as it will be handled entirely within the GUI implementation.

8. > For simplicity, you may assume there is a _single system_ running the app (no communication or saving between devices is necessary).

This is not represented in this design, as it will be handled entirely within the GUI implementation.
