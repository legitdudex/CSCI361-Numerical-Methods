#include <cstdio>
#include <cstdlib>
#include <math.h>

class MathFunction{
public:
	virtual double f(double x) const { return 0; };
	virtual void ffp(double x, double &f, double &fprime) const{};
	virtual double fxy(double x, double y) const { return 0; };

	virtual ~MathFunction() {}

protected:
	MathFunction() {}
};

class MathLibraryCPP: public MathFunction{	
public:
	static int bisection(const MathFunction &mf, double target, double tol_f, double tol_x, int max_iter, double x_low, double x_high, double &x, int &num_iter);
	
	static int NewtonRaphson(const MathFunction &mf, double target, double tol_f, double tol_x, int max_iter, double x0, double &x, int &num_iter);

	static int secant(const MathFunction &mf, double target, double tol_f, double tol_x, int max_iter, double x0, double x1, double *x, int &num_iter);
	
private:
	MathLibraryCPP();
};

class MySquare: public MathFunction{
public:
	virtual double f(double x) const{
		return x * x;
	}
	virtual void ffp(double x, double &f, double &fprime) const{
		f = x * x;
		fprime = 2.0 * x;
	}
};


