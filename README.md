README
Built In Tester API									
	
	Authors:																			
		Kostyantyn Proskuryakov, Ian Johnson              								
            																			
	A logic testing API that works within the code and gives meaningful and readable	 
		output. 																		
																						
	To Install: Copy the src folder into your project directory.						
																						
	Javadoc: The javadoc is completely set up for the API, simply generate the 			
		javadoc in whichever IDE you are using to access it.							
		
	To Use: All usage intstructions are in the javadoc as well as documented here.		
																								
		enable(programName: String, outputFileName: String)								
			Call this method at the start of execution. Comment this method out to  	
			disable the entire API. The other methods can remain in the code and    	
			they will take up very little execution time.								
																						
		expecting(logMessage: String, parameters: Object...)							
			Sets the expectation that if a certain set of parameters are the wanted 	
			values, the log message specified will be the one that is obtained from 	
			log(String). This method should be peppered at the beginning of any 		
			method that needs logic testing. Several should be created with 			
			different parameters and their associated expected messages to provide 		
			as much logic testing as possible.											
																						
		log(message: String)															
			Should be used right before any return statement. The logs will show the	
			message upon return.														
																						
		logOutput()																		
			Should be called at the very end of execution. Creates the output log as	
			an html page under the project directory in the folder designated by 		
			programName and the filename designated by outputFileName with three 		
			digits at the end that increment by 1 with every new output log created.	 
			File directory structure is cross-platform.									
																						
	Acknowledgements:																	
		Christian Bach - tablesorter http://tablesorter.com/docs/						
			License: http://opensource.org/licenses/mit-license.php						
																						
				  Use only for the IDT 2014 Winter HS competition.					
