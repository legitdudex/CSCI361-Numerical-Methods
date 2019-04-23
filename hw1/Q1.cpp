#include <cstdio>
#include <cstdlib>
#include <iostream>
#include <string>

long gcd_euclid (long a, long b){
	if((a = 0) || (b = 0)){
		if(a == 0){ return b; }
		else if(b == 0){ return a; }
	}
	else{
		if(a < b) { return gcd_euclid(b, a); }
		long c = (a % b);
		if(c == 0){ return b; }
		else if(c == 1){
			return 1;
		}

		return gcd_euclid(b, c);
	}
}

long LCM(long a, long b){
	return ((a * b) / gcd_euclid(a, b)); 
}

int main(){
	std::cout << "HW1.1: GCD" << std::endl;
	std::cout << "Enter either gcd or lcm or exit: ";
	std::string func;
	getline(std::cin, func);
	std::cout << std::endl;
	if((func.compare("gcd") != 0) || (func.compare("lcm") != 0) || (func.compare("exit") != 0)){
		std::cout << "Error, invalid input" << std::endl;
		return 0;
	}
	else if(func == "exit"){ return 0; }
	else{
		if(func.compare("gcd") == 0){
			long a, b;
			std::cout << "Enter a value for a: ";
			std::cin >> a;
			std::cout << std::endl << "Enter a value for b: ";
			std::cin >> b;
			if(a < 0 || b < 0){
				std::cout << std::endl << "Error, invalid input for a or b" << std::endl;
				return 0;
			}
			std::cout << std::endl <<  "The greatest common divisor of " << a << " and " << b << " is " << gcd_euclid(a, b) << std::endl;
		}
		else if(func.compare("lcm") == 0){
			long a, b;
			std::cout << "Enter a value for a: ";
                        std::cin >> a;
                        std::cout << std::endl << "Enter a value for b: ";
                        std::cin >> b;
			if(a < 0 || b < 0){
				std::cout << std::endl << "Error, invalid input for a or b" << std::endl;
				return 0;
			}
                        std::cout << std::endl << "The least common multiple of " << a << " and " << b << " is " << LCM(a, b) << std::endl;
		}
	}
	return 0;
}
