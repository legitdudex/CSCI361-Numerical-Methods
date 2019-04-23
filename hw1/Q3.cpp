
#include <cstdio>
#include <cstdlib>
#include <iostream>
#include <cmath>


int factorial(int x){
	int result = 1;
	for(int i = x; i > 0; i--){
		result*=i;
	}
	return result;
}

double exp_sum(double x, int n_max){
	double result = 1 + x;
	for(int i = 2; i < n_max + 1; i++){
		result += (pow(x, i) / factorial(i));
	}
	return result;
}


int main(){
	std::cout << "HW1.3: HORNER'S RULE (NESTED SUMNATION) AND TAYLOR SERIES" << std::endl << std::endl;
	int n_max;
	
	std::cout << "Enter desired value for n_max: ";
	std::cin >> n_max;
	if(n_max < 0 || n_max == 0){
		std::cout << "Error, invalid input for n_max" << std::endl;
		return 0;
	}

	double x;
	std::cout << "Enter desired value for x: ";
	std::cin >> x;
	double ourResult = exp_sum(x, n_max);
	double expectedResult = exp(x);
	std::cout << "Our result: " << ourResult << std::endl;
	std::cout << "Expexted result exp(x) = " << expectedResult << std::endl;
	std::cout << "Absolute difference |exp_sum(x) - exp(x)| = " << abs(ourResult - expectedResult) << std::endl;

	return 0;
}
