import java.util.*;
import java.lang.Math;

public class spin_configuration extends quantum_spin{
	quantum_spin head;
	quantum_spin tail;
	private int length;
	private float B;
	private float C;
	private float T;	
	public spin_configuration(float B, float C, float T){
	/*
 	1. This constructor method will create our first configuration of quantum spins in a circularly linked list fashion.
	2. We want an even number of spins, as stated in the project guidelines.
	*/
	this.head = new quantum_spin();
	this.tail = new quantum_spin();
	this.head.setSpin(7); //header
	this.tail.setSpin(8); //trailer
	//both head and tail spins are set to 7 and 8 respectively for us to be able to easily identify them.
	
	this.head.next = this.tail;
	this.tail.next = this.head; //have to make this a circularly linked list of spin nodes. 
	this.length = 0;
	this.B = B;
	this.C = C;
	this.T = T;
	}
	
	public void insertNewSpin(spin_configuration c){
	/*
 	This method inserts a new quantum_spin object at the beginnning of the list.
	*/
		quantum_spin pointer = c.head;
		quantum_spin newSpin = new quantum_spin();
		newSpin.next = pointer.next;
		pointer.next = newSpin;
		c.length++;	
	}

	public int getLength(){ return this.length; }

	public float getMagnetization(){
	/*
 	This method returns the magnetization value per spin of the current configuration of quantum spins using the formula as stated in the project guidelines.
	RETURNS: A floating point value between -1 and 1.
	*/
		float magnetization = 0;
		quantum_spin pointer = this.head.next; //start at the beginning
		while(pointer != this.tail){
			magnetization += pointer.getSpin();
			pointer = pointer.next;
		}
		return (magnetization/getLength());
	}
	
	public float getPairCorrelation(){
	/*
 	This method returns the pair correlation value per spin of the current configuration of quantum spins using the forumula as stated in the project
		guidelines.
	RETURNS: A floating point value between -1 and 1.
	*/
		float pairCorrelation = 0;
		quantum_spin pointer = this.head.next; //start at the beginning
		while(pointer != this.tail){
			if(pointer.next == this.tail){
				pairCorrelation += (pointer.getSpin() * this.head.next.getSpin());
			}
			else{ pairCorrelation += (pointer.getSpin() * pointer.next.getSpin()); }
			pointer = pointer.next;
		}
		return (pairCorrelation/getLength());
	}
	
	public float getEnergy(){
		float energy = 0;
		quantum_spin pointer = this.head.next; //start at the beginning
		while(pointer != this.tail){
			if(pointer.next == this.tail){
				energy += ((this.B * pointer.getSpin()) + (this.C * pointer.getSpin() * this.head.next.getSpin()));
			}
			else{ energy += ((this.B * pointer.getSpin()) + (this.C * pointer.getSpin() * pointer.next.getSpin()));
			}
			pointer = pointer.next;
		}
		return -(energy);
	}

	public float getWeightFactor(){
		return (float)Math.pow(Math.E, (-getEnergy()/this.T));
	}

	public spin_configuration createConfiguration(int n){
	/*
 	This method creates a configuration of length/size n.
	*/
		if((n % 2) != 0){ n += 1; } //we need the size of the configuration to be even
		for(int i = 0; i < n; i++){
			insertNewSpin(this);
		}
		return this; //return the new configuration
	}


	public spin_configuration changeConfiguration(){
	/*
 	This is the essential function required to change the current spin configuration to a possible more efficient configuration.
	I generated a random number in between 1 and the length of the configuration using java's built in random number generator using correct syntax.
	Of course, we need to import the package to use Random under java.util.
	*/
		Random random = new Random();
		int selectedSpin = random.nextInt(getLength() - 1) + 1; //generate a random number in between the length of our configuration and 1.
		spin_configuration newConfig = new spin_configuration(this.B, this.C, this.T);
		for(int i = 0 ; i < getLength(); i++){
			insertNewSpin(newConfig);
		}
		quantum_spin pointer = this.head; //need to reset our cursor.
		quantum_spin npointer = newConfig.head; 
		for(int i = 0; i < getLength(); i++){
			npointer.setSpin(pointer.getSpin()); //copy over the spin from the original configuration to the new one
			if(i == selectedSpin){ npointer.changeSpin(); } //if we are at our randomly selected index, change the spin
			pointer = pointer.next;
			npointer = npointer.next;
		}
		return newConfig;//return the new configuration	
	}

	public spin_configuration setInitialConfiguration(){
	/*
	*** If C >= 0 then our initial configuration will consist of all positive spin nodes. Else, they will be alternating ***
	*/
		quantum_spin pointer = this.head.next; //start at the beginning
		if(this.C >= 0){
			for(int i = 0; i < getLength(); i++){
				pointer.setSpin(1);
				pointer = pointer.next;
			}
		}
		else{
			for(int i = 1; i < getLength() + 1; i++){
				if((i % 2) == 0){
					pointer.setSpin(1);
				}
				else{ pointer.setSpin(-1); }
				pointer = pointer.next;
			}
		}
		return this;
	}
	

}


