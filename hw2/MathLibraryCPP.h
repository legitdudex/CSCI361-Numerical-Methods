

#include <cstdio>
#include <cstdlib>
#include <math.h>



class MathLibraryCPP: public MathFunction{
public:
	static int bisection(const MathFunction &mf, double target, double tol_f, double tol_x, int max_iter, double x_low, 
		double x_high, double &x, int &num_iter);
	
	static int NewtonRaphson(const MathFunction &mf, double target, double tol_f, double tol_x, int max_iter, double x0,
		double &x, int &num_iter);

	static int secant(const MathFunction &mf, double target, double tol_f, double tol_x, int max_iter, double x0, double x1,
		double &x, int &num_iter);

public:
	MathLibraryCPP() {}
};
