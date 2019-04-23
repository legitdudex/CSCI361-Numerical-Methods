import java.util.*;
import java.util.concurrent.TimeUnit;

public class proj2 implements Runnable{
	int nfc;
	int nc;
	float Bc;
	float Cc;
	float Tc;
	ArrayList<ArrayList> masterLstc;
/*
 * This is our main class for project 2. 
 * The project will be executed in this class.
 * This class only contains the main function that executes our project.
 *
 * This class will be using itself as a thread in the run method in the bottom of this class definition in order to run many calculations in parallel.
 	For this project, since this is the first time I have been asked to run more than 10 or so threeads, I have limited the program to run 100 threads
 * 	at a time, which should be more than efficient enough to have a noticible time-saving effect. Therefore, the main function of this class will run 10
 * 	iterations, each iteration consisting of the creation, life, and death of 100 threads, putting our thread count at 1000, as required by the project
 * 	guidelines.
 */

		
	 public proj2(ArrayList<ArrayList> masterLst, int nf, int n, float B, float C, float T){
        /*
 	To store our params for use in the thread's run method
        */
                this.nfc = nf;
                this.nc = n;
                this.Bc = B;
                this.Cc = C;
                this.Tc = T;
                this.masterLstc = masterLst;
        }

	public static ArrayList<Float> metropolize(int nf, int n, float B, float C, float T){
	/*
 	To make things as modular as possible, I have separated the metropolis algorithm from the thread's running definition in order to maximize
		readability and minimize clutter.
	*/
		float averageM = 0;
		float averageCp = 0;
		float weightFactorSum = 0;
		spin_configuration c = new spin_configuration(B, C, T);
		c = c.createConfiguration(n);
		c = c.setInitialConfiguration(); //set our initial configuration (all 1's if C >= 0, else alternating 1's and -1's
		for(int i = 0; i < nf; i++){
		//this is where we change the config n times, regardless of whether we keep the new config or not
			spin_configuration newC = c.changeConfiguration();
			float energyDifference = newC.getEnergy() - c.getEnergy(); //to change if we can find a more efficient method
			if(energyDifference < 0){
				c = newC; //change our config to the new one
			}
			else{
				float p = (float)Math.pow(Math.E, -energyDifference/T);
				Random random = new Random();
				float r = (0 + random.nextFloat()) * (float)(0.99 - 0);
				if(r < p){
					c = newC;
				} //else, reject
			}
			
			averageM += c.getMagnetization(); //we need to sum the magnetization per config change
			averageCp += c.getPairCorrelation(); //we need to sum the pair correlation per config change
			weightFactorSum += c.getWeightFactor();
		}
		//now we have our "optimal configuration" as stated in the project guidelines.
		//it is now time to calculate our m and pc values
		averageM = averageM/n; //average it
		averageCp = averageCp/n; //average it
		ArrayList<Float> mresult = new ArrayList<Float>();
		mresult.add(averageM);
		mresult.add(averageCp);
		return mresult;
	}	
	

	public void run(){
		this.masterLstc.add(metropolize(this.nfc, this.nc, this.Bc, this.Cc, this.Tc)); //all we need to do per thread is run the metropolis algorithm
	}
	
	public static float mtheory(float B, float C, float T){
                if(C == 0){
                        float dividend = (float)(Math.pow(Math.E, (B/T)) - Math.pow(Math.E, (-B/T)));
                        float divisor = (float)(Math.pow(Math.E, (B/T)) + Math.pow(Math.E, (-B/T)));
                        return dividend/divisor;
                }
                else if(B == 0){
                        return 0;
                }
		return 0;
        }

        public static float cptheory(float B, float C, float T){
                if(C == 0){
                        return (float)Math.pow(mtheory(B, C, T), 2);
                }
                else if(B == 0){
                        float dividend = (float)(Math.pow(Math.E, (C/T)) - Math.pow(Math.E, (-C/T)));
                        float divisor = (float)(Math.pow(Math.E, (C/T)) + Math.pow(Math.E, (-C/T)));
                        return dividend/divisor;
                }
		return 0;
        }


	public static float calculateuM(ArrayList<ArrayList> masterLst){
		float result = 0;
		for(int i = 0; i < masterLst.size(); i++){
			result += (float)(masterLst.get(i)).get(0); //this adds the first value of every list in the master list	
	}
		return result/masterLst.size(); //average it over all threads
	}

	public static float calculateuC(ArrayList<ArrayList> masterLst){
		float result = 0;
		for(int i = 0; i < masterLst.size(); i++){
			result += (float)(masterLst.get(i)).get(1); //this adds the second value of every list in the master list
		}
		return result/masterLst.size(); //average it over all threads
	}

	public static float calculateRelativeError(ArrayList<ArrayList> masterLst, float B, float C, float T){
		float relativeError = 0;
		float cpThe = cptheory(B, C, T);
		for(int i = 0; i < masterLst.size() - 1; i++){
			float firstStep = (float)((masterLst.get(i)).get(1)) -  cpThe;
			relativeError += (firstStep / cpThe);	
		}
		return relativeError/masterLst.size();
	}

	public static float calculateVariance(ArrayList<ArrayList> masterLst, float B, float C, float T){
		float variance = 0;
		for(int i = 0; i < masterLst.size(); i++){
			float firstVal = (float)(masterLst.get(i)).get(1) - cptheory(B, C, T);
			firstVal = firstVal / cptheory(B, C, T);
			firstVal -= calculateRelativeError(masterLst, B, C, T);
			variance += (float)Math.pow(firstVal, 2);
		}
		return variance/masterLst.size();
	}

	public static ArrayList<Float> challenge1(){
		/*
 		This is code for challenge 1
		*/
		//we want to find values of B and C where <m> would hold 0 ant T == 0 and hold high when T > 0 
		//We can easily find these values by using a brute force method.
		ArrayList<Float> returnLst = new ArrayList<Float>();
		int nf = 52; //we have found out the optimal nf
		int n = 100; //100 spins per config
		//we will try to solve the whole challenge in code snippet below for efficiency
		float B1 = 0;
		float C1 = 0;
		boolean passed = false; //we will see if the positive values for B and C work for this
		boolean passed2 = false; //to test for when T > 0
		for(int i = 0; i < 10; i++){
			B1 = i;
			C1 = i;
			if(metropolize(nf, n, B1, C1, 0).get(0) == 0){ //keep in mind this function returns a list of <m> and <cp> at indexes 0 and 1 respectively 
				passed = true;
				returnLst.add(B1);
				returnLst.add(C1);
				if(metropolize(nf, n, B1, C1, 1).get(0) == 0){
					returnLst.add(B1);
					returnLst.add(C1);
					return returnLst;
				}
			}
		}
		if(passed == false){ //if the first iteration did not work, then let's try both B and C negative
			for(int i = 0; i > -10; i--){
				B1 = i;
				C1 = i;
				if(metropolize(nf, n, B1, C1, 0).get(0) == 0){
					passed = true;
					returnLst.add(B1);
					returnLst.add(C1);
					if(metropolize(nf, n, B1, C1, 1).get(0) == 0){
                                        	returnLst.add(B1);
                                        	returnLst.add(C1);
                                        	return returnLst;
                                	}
				}
			}
			if(passed == false){
				for(int i = 0; i < 10; i++){ //now let's alternate B and C's signs
					B1 = i;
					C1 = -i;
					if(metropolize(nf, n, B1, C1, 0).get(0) == 0 || metropolize(nf, n, -B1, -C1, 0).get(0) == 0){
						passed = true;
						returnLst.add(B1);
						returnLst.add(C1);
						if(metropolize(nf, n, B1, C1, 1).get(0) == 0){
                                        		returnLst.add(B1);
                                        		returnLst.add(C1);
                                        		return returnLst;
                                		}
					}
				}
			}
		}
		return returnLst;
	}

	public static void main(String[] args) throws InterruptedException{
		Scanner s = new Scanner(System.in);
		//System.out.print("\nEnter the value for nf: ");
		int nf = 30;// = Integer.parseInt(s.nextLine()); //get the value for the number of flips
		System.out.print("\nEnter the value for B: ");
		float B = s.nextFloat(); //get the value for B
		System.out.print("\nEnter the value for C: ");
		float C = s.nextFloat(); //get the value for C
		System.out.print("\nEnter the value for T: ");
		float T = s.nextFloat(); //get the value for T
		System.out.println(""); //to clean up
		int n = 100; //as stated in the project documentation
		System.out.println("Your initial configuration is as follows:\n Number of flips: " + Integer.toString(nf) + "\n B: " + String.valueOf(B) + "\nC: " + String.valueOf(C) + "\n T: " + String.valueOf(T) + "\nYour program is starting with an arbritrary value of n = 100, as stated in the project documentation.\n");
		TimeUnit.SECONDS.sleep(2);
		System.out.println("Starting...\n");
		ArrayList<ArrayList> masterLst = new ArrayList<ArrayList>();
		//now we will start our threads//
		//500 each to avoid overflowing the cpu//
		while(true){
			masterLst = new ArrayList<ArrayList>(); //we have to redefine our master list for every iteration
			for(int i = 0; i < 2; i++){
				for(int j = 0; j < 500; j++){
					Runnable runThread = new proj2(masterLst, nf, n, B, C, T);
					new Thread(runThread).start();
				}
			}
			float relativeError = calculateRelativeError(masterLst, B, C, T);
			float variance = calculateVariance(masterLst, B, C, T);
			System.out.println("Our value for relative error for " + Integer.toString(nf) + " configuration changes is: " + String.valueOf(relativeError));
			System.out.println("Our value for variance for " + Integer.toString(nf) + " configuration changes is: " + String.valueOf(variance));
			if(Math.abs(relativeError) <= 0.02){
				System.out.println("\n\n\n\n\n\n\n\nSuccess! We have met the conditions specified for variance and relative error.");
				System.out.println("Our relative error is: " + String.valueOf(relativeError) + " <= 0.02");
				System.out.println("We have reached these values by changing the configuration of each thread " + Integer.toString(nf) + " times.");
				break;
			}
			nf++;
		}
		System.out.println("Now we can try out the challenges!\nSummary:\n");
		System.out.println("Our value for uM (average over all threads): " + String.valueOf(calculateuM(masterLst)));
		System.out.println("Our value for uC (average over all threads): " + String.valueOf(calculateuC(masterLst)));
		

	}








} 
