
#include <cstdio>
#include <cstdlib>
#include <math.h>


double cum_norm(double x){
	const double root = sqrt(0.5);
	return 0.5 * (1.0 + erf(x * root));
}

class MyCumNorm : public MathFunction {
public:
	virtual double f(double x) const{ return cum_norm(x); }
	virtual void ffp(double x, double &f, double &fprime) const{
		const double pi = 4.0 * atan2(1.0, 1.0);
		f = cum_norm(x);
		fprime = exp(-0.5 * x * x) / sqrt(2.0 * pi);
	}
};







