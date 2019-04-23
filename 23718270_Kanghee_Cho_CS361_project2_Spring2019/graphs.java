
import java.util.*;
import java.lang.Math;


public class graphs{
	public static float mtheory(float B, float C, float T){
		if(C == 0){
			float dividend = (float)(Math.pow(Math.E, B/T) - Math.pow(Math.E, -B/T));
			float divisor = (float)(Math.pow(Math.E, B/T) + Math.pow(Math.E, -B/T));
			return dividend/divisor;
		}
		else if(B == 0){
			return 0;
		}
		else{
			return (float)1000;
		}
	}
	
	public static float cptheory(float B, float C, float T){
		if(C == 0){
			return (float)Math.pow(mtheory(B, C, T), 2);
		}
		else if(B == 0){
			float dividend = (float)(Math.pow(Math.E, C/T) - Math.pow(Math.E, -C/T));
			float divisor = (float)(Math.pow(Math.E, C/T) + Math.pow(Math.E, -C/T));
			return dividend/divisor;
		}
		else{
			return (float)1000;
		}
	}

	
	/*
 	This main function is only necessary if you wish to test out these function individually. 
	Otherwise, I have implemented the main function in proj_2.java to use them to calculate the resulting 
		values for each thread iteration.
	*/
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		System.out.println("Starting graphs/equations test.\n\n\n");
		System.out.println("Pick any of the equations you wish to test below and enter the name in the prompt as shown\n");
		System.out.println("1. <m> theory-> Type 'mtheory'\n2. <cp> theory-> Type 'cptheory'\n");
		System.out.println("To exit, type exit or quit after the prompt and hit enter\n");
		while(true){
			System.out.print("->");
			String input = s.nextLine();
			if(input.equals("exit") || input.equals("Exit") || input.equals("quit") || input.equals("Quit")){
				break;
			}
			else if(input.equals("mtheory")){
				System.out.println("Enter the value you wish to allocate for B: ");
				float B = s.nextFloat();
				System.out.println("Enter the value you wish to allocate for C: ");
				float C = s.nextFloat();
				System.out.println("Enter the value you wish to allocate for T: ");
				float T = s.nextFloat();
				System.out.println("\n\n\nB = " + Float.toString(B) + "\nC = " + Float.toString(C) + "\nT = " + Float.toString(T));
				float result = mtheory(B, C, T);
				System.out.println("The value for <m>Theory is: " + String.valueOf(result));
				break;
			
			}
			else if(input.equals("cptheory")){
                                System.out.println("Enter the value you wish to allocate for B: ");
                                float B = s.nextFloat();
                                System.out.println("Enter the value you wish to allocate for C: ");
                                float C = s.nextFloat();
                                System.out.println("Enter the value you wish to allocate for T: ");
                                float T = s.nextFloat();
                                System.out.println("\n\n\nB = " + Float.toString(B) + "\nC = " + Float.toString(C) + "\nT = " + Float.toString(T));
				float result = cptheory(B, C, T);
                                System.out.println("The value for <cp>Theory is: " + String.valueOf(result));
                        	break;
			}
			else{ break; } 
		}
	}
}
