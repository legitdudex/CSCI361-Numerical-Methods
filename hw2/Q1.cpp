#include <cstdio>
#include <cstdlib>
#include <iostream>
#include <math.h>
#include "MathFunction.h"

int MathLibraryCPP::bisection(const MathFunction &mf, double target, double tol_f, double tol_x, int max_iter, 
	double x_low, double x_high, double &x, int &num_iter){

	x = 0;
	num_iter = 0;
	double y_low = mf.f(x_low);
	double diff_y_low = y_low - target;

	if(abs(diff_y_low) <= tol_f){
		x = x_low;
		return 0;
	}
	
	double y_high = mf.f(x_high);
	double diff_y_high = y_high - target;
	if(abs(diff_y_high) <= tol_f){
		x = x_high;
		return 0;
	}
	if(diff_y_low * diff_y_high > 0.0){
		x = 0;
		return 1;
	}
	for(num_iter = 1; num_iter < max_iter; num_iter++){
		x = (x_low + x_high) / 2.0;
		double y = mf.f(x);
		double diff_y = y - target;
		if(abs(diff_y) <= tol_f){
			return 0;
		}
		if(diff_y * diff_y_low > 0.0){
			x_low = x;
		}
		else{ x_high = x; }
		
		if(abs(x_high - x_low) <= tol_x){
			return 0;
		}
	}
	x = 0;
	num_iter = max_iter;
	return 1;
}

int main(){
//now time to test our bisection funciton
	MySquare sq;
	double x, x1, x2;
	int num_iter = 0, num_iter1=  0, num_iter2 = 0;
	int result1 = MathLibraryCPP::bisection(sq, 4.0, 1.0e-6, 1.0e-6, 100, 0.0, 5.0, x, num_iter);
	int result2 = MathLibraryCPP::bisection(sq, 4.0, 1.0e-6, 1.0e-6, 100, 5.0, 5.0, x1, num_iter1);
	int result3 = MathLibraryCPP::bisection(sq, -1.0, 1.0e-6, 1.0e-6, 100, 10.0, 0.0, x2, num_iter2);
	
	std::cout << "Results for when x_low = 0.0 and x_high = 5.0 is: " << std::endl;
	std::cout << "x: " << x << "    " << "num_iter: " << num_iter << std::endl << std::endl;
	
	if(result2 != 1){
		std::cout << "Results for when x_low = 5.0 and x_high = 5.0 is: " << std::endl;
		std::cout << "x: " << x1 << "    " << "num_iter: " << num_iter1 << std::endl << std::endl;
	}
	else{
		std::cout << "Test for when x_low = 5.0 and x_high = 5.0 failed." << std::endl;
	}
	if(result3 != 1){
		std::cout << "Results for when x_low = 10.0 and x_high = 0.0 is: " << std::endl;
		std::cout << "x: " << x2 << "    " << "num_iter: " << num_iter2 << std::endl << std::endl;
	}
	else{
		std::cout << "Test for when x_low = 10.0 and x_high = 0.0 failed." << std::endl;
	}
return 0;
} 
