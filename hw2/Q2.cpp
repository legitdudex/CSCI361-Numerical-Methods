
#include <cstdio>
#include <cstdlib>
#include <iostream>
#include <math.h>
#include "MathFunction.h"

int MathLibraryCPP::NewtonRaphson(const MathFunction &mf, double target, double tol_f, double tol_x, int max_iter,
	double x0, double &x, int &num_iter){

	const double tol_fprime = 1.0e-12;
	double f = 0;
	double fprime = 0;
	
	x = x0;
	for(num_iter = 1; num_iter < max_iter; num_iter++){
		mf.ffp(x, f, fprime);
		double diff_f = f - target;
		if(abs(diff_f) <= tol_f){
			return 0;
		}
		if(abs(fprime) <= tol_fprime){
			x = 0;
			return 1;
		}
		
		double delta_x = diff_f / fprime;
		if(abs(delta_x) <= tol_x){
			return 0;
		}
		x -= delta_x;
	}
	
	x = 0;
	num_iter = max_iter;
	return 1;
}

int main(){
//now time to test our Newton Raphson funciton
	MySquare sq;
        double x, x1, x2;
        int num_iter = 0, num_iter1=  0, num_iter2 = 0;
        int result1 = MathLibraryCPP::NewtonRaphson(sq, 4.0, 1.0e-6, 1.0e-6, 100, -5.0, x, num_iter);
        int result2 = MathLibraryCPP::NewtonRaphson(sq, 4.0, 1.0e-6, 1.0e-6, 100, 1.0, x1, num_iter1);
        int result3 = MathLibraryCPP::NewtonRaphson(sq, -1.0, 1.0e-6, 1.0e-6, 100, 3.0, x2, num_iter2);

        std::cout << "Results for when x0 was -5.0 was: " << std::endl;
        std::cout << "x: " << x << "    " << "num_iter: " << num_iter << std::endl << std::endl;

        if(result2 != 1){
                std::cout << "Results for when x0 was 1.0 is: " << std::endl;
                std::cout << "x: " << x1 << "    " << "num_iter: " << num_iter1 << std::endl << std::endl;
        }
        else{
                std::cout << "Test for when x0 was 1.0 failed." << std::endl;
        }
        if(result3 != 1){
                std::cout << "Results for when x0 was 3.0 is: " << std::endl;
                std::cout << "x: " << x2 << "    " << "num_iter: " << num_iter2 << std::endl << std::endl;
        }
        else{
                std::cout << "Test for when x0 was 3.0 failed." << std::endl;
        }


return 0;
}

