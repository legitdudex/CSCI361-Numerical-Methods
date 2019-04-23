#include <cstdio>
#include <cstdlib>
#include "point.h"

point::point(double x, double y){
	x_value = x;
	y_value = y;
}
int point::get_x(){ return x_value; }
int point::get_y(){ return y_value; } 
