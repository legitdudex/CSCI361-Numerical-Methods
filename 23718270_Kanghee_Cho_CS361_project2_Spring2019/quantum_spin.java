import java.util.*;

public class quantum_spin{
	private int spin;
	quantum_spin next = null;
	
	public quantum_spin(){
	/*
	This is the constructor function for our spin class. 
	This method sets the spin of our quantum_spin object and initializes it's next value to null.
	*/
		Random random = new Random();
		boolean randBool = random.nextBoolean(); //to generate either a -1 or a 1, randomly.
		if(randBool){
			this.spin = 1;
		}
		else{ this.spin = -1; } 
	}
	
	public int getSpin(){ return this.spin; } //self explanatory getter function for the private spin value	

	public void changeSpin(){
	/*
 	If we need to change the spin value of a quantum_spin object
	*/
		if(this.spin == 1){
			this.spin = -1;
		}
		else{ this.spin = 1; }
	}
		
	public void setSpin(int spin){
	/*For use when setting up the inital configuraiton*/
		this.spin = spin;
	}
}
