
#include <cstdio>
#include <cstdlib>
#include <iostream>
#include <vector>
#include <algorithm>

int dec_to_base(int a, int base, std::vector<char> &digits){
	if((base < 2) || (base > 16) || (a < 0)){ return 1; }	//fail cases
	digits.clear();

	if(a == 0){ 
		digits.push_back('0');
		return 0; 
	}
	char alphanum[16] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	while(a > 0){
		int rem = a % base;
		digits.push_back(alphanum[rem]);
		a/=base;
	}
	
	std::reverse(digits.begin(), digits.end());
	return 0;
}

int main(){
	std::cout << "HW1.2: CONVERT DECIMAL TO BINARY, DECIMAL TO HEX" << std::endl;
	std::vector<char> digits;
	int base;
	int a;
	int status;
	std::cout << "Enter a decimal value you want to convert (-1 to quit): ";
	while(true){
		std::cin >> a;
		if(a == -1){ break; }
		std::cout << "Choose a base value from 2 to 16: ";
                std::cin >> base;
		int status = dec_to_base(a, base, digits);
		if(status == 1){
			std::cout << "Error: invalid input" << std::endl;
		}
		else{
			for(int i = 0; i < (int) digits.size(); i++){
                		std::cout << digits[i];
        		}
			std::cout << std::endl;
		}
		std::cout << "Enter a new value (-1 to quit): ";
	}
	return 0;
}
