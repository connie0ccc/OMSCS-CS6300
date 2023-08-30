# JobCom Design Description

This document addresses the requirements for the JobCom app being developed for this course. The system will provide insight to the end user when deciding on job offers. 

In this document, we will discuss how the proposed design will address the system requirements as defined in the project description. Some requirements are implementation specific and do not pertain directly to the design. Those requirements will be identified below. 



# Requirements

 1. When the app is started, the user is presented with the main menu, which allows the user to (1) enter or edit current job details, (2) enter job offers, (3) adjust the comparison settings, or (4) compare job offers (disabled if no job offers were entered yet).

	* **While most of this requirement will be addressed in the GUI, there is one piece of this requirement which needs to be addressed during the design.  There needs to be a way for the system to know if a comparison is possible. The SystemMgr class will contain the logic to check if a comparison is possible. The logic will be in a method called comparisonPossible() and will return True in the following situations:**	
		* **if currentJob() returns a Job and offersAvailable() is greater than or equal to 1**
		* **if offersAvailable() is greater than or equal to 2**
	
	* **During our team discussion, we decided that the SystemMgr class will also provide the logic to calculate the Job score.  We added the calcScore() function to do this and made it private.**
    
 2. When choosing to enter current job details, a user will:
    
	* Be shown a user interface to enter (if it is the first time) or edit all the details of their current job, which consist of:
    
		* Title
		* Company
		* Location (entered as city and state)
		* Cost of living in the location (expressed as an [index](https://www.expatistan.com/cost-of-living/index/north-america))    
		* Yearly salary    
		* Yearly bonus    
		* Restricted Stock Unit Award (expressed as a lump sum vested over 4 years)    
		* Relocation stipend (A single value from $0 to $25,000)    
		*Personal Choice Holidays (A single overall number of days from 0 to 20)
	* Be able to either save the job details or cancel and exit without saving, returning in both cases to the main menu.
    
      * **This requirement is handled in the SystemMgr with the calls to createCurrentJob() and updateCurrentJob(). The system will know if the user has a job through the use of the currentJob() method. This method will return the current job if the user is currently employed. It will return nothing if there is no current job. All three of these methods make calls to the Job class to read, insert, and update Job data as appropriate.**

	  * **During our team discussion, we only made minor changes to the Job class as it already captured all of the requirements.  We made slight name changes (costOfLivingIndex) and added the ranges to relocationStipend and holidays.**

 3. When choosing to enter job offers, a user will:
    * Be shown a user interface to enter all the details of the offer, which are the same ones listed above for the current job.
      * **This requirement is handled through the GUI**
    
    * Be able to either save the job offer details or cancel.
      * **This requirement is handled through the createJobOffer() on SystemMgr. This method calls the insert() method on the Job class.**
    * Be able to (1) enter another offer, (2) return to the main menu, or (3) compare the offer (if they saved it) with the current job details (if present).
      * **This requirement is handled through the GUI and through a call to currentJob() on SystemMgr. If this method returns a job, the user is currently employed and is able to compare the job offer with their current job.**

 4. When adjusting the comparison settings, the user can assign integer weights to:
    
	* Yearly salary    
	* Yearly bonus    
	* Restricted Stock Unit Award    
	* Relocation stipend    
	* Personal Choice Holidays
      * **This requirement is handled through updateComparisonSettings() method on SystemMgr. That method will call update() on ComparisonSettings object.**

	  * **The only changes we made were to clarify that the ComparisonSettings attributes are weights by appending "Weight" to the end of each attribute name.**
    
	If no weights are assigned, all factors are considered equal.
	* **In the compareJobs() and listJobs() methods, if there are no weights on the ComparisonSettings class, the system will treat all factors equal. If there are weights, they will be applied according to the job ranking requirement.**

	* **Not completely necessary, but we thought it would make it clearer to initialize the weight attributes to 1.**

5.  When choosing to compare job offers, a user will:
	* Be shown a list of job offers, displayed as Title and Company, ranked from best to worst (see below for details), and including the current job (if present), clearly indicated.
	  * **This requirement is handled through the listJobs() method in the SystemMgr. This method will call the getJobs() method on the Job class.**
	* Select two jobs to compare and trigger the comparison.
	  * **This requirement is handled through the compareJobs() method in the SystemMgr. This method will call the getJob() method on the Job class for each Job selected for comparison. The compareJobs() method will supply the data for the table comparing the two jobs.**
	* Be shown a table comparing the two jobs, displaying, for each job:
		* Title
		* Company
		* Location
		* Yearly salary adjusted for cost of living
		* Yearly bonus adjusted for cost of living
		* Restricted Stock Unit Award
		* Relocation stipend
		* Personal Choice Holidays
        
		  * **The data for this table is the return value of compareJobs() as described above.**

	* Be offered to perform another comparison or go back to the main menu. 
      * **This requirement is handled through the GUI**

6. When ranking jobs, a job’s score is computed as the weighted sum of:  
      
	* AYS + AYB + (RSUA / 4) + RELO + (PCH * AYS / 260)       
	  
		where:  
			AYS = yearly salary adjusted for cost of living  
			AYB = yearly bonus adjusted for cost of living  
			RSU = restricted stock unit award
			RELO = relocation stipend
			PCH = personal choice holidays

	* For example, if the weights are 2 for the yearly salary, 2 for relocation stipend and 1 for all other factors, the score would be computed as:

		2/7 * AYS + 1/7 * AYB + 1/7 * (RSUA / 4) + 2/7 * RELO + 1/7 * (PCH * AYS / 260)

	* **This requirement is handled in the SystemMgr class in the *listJobs()* and *compareJobs()* operations.  In these operations, the calculation above is applied after the Jobs are retrieved and before they are returned to the caller. To retrieve the weights, there is a getComparisonSettings() method on the ComparisonSettings class.**
  
7.  The user interface must be intuitive and responsive.

	* **This is not represented in the design as it is non-functional requirement and general property of the entire system. It will be important to keep this in mind during implementation and throughout the development lifecycle.**
 
    
9. For simplicity, you may assume there is a single system running the app (no communication or saving between devices is necessary).

	* **This is not represented in the design, as it does not necessarily show something that is required. Rather it is something that can be assumed for ease of system design.** 

