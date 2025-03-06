#include <stdio.h>
#include <math.h>

typedef double (*functype)(double);

double findzero(functype f, double a, double b, double eps) {
    if (f(a) * f(b) >= 0) {
        printf("Error: the function must have different signs at the ends of the interval.\n");
        return NAN;
    }

    double c, prev_c = a;
    do {
        c = (a + b) / 2.0;
        if (f(c) == 0.0) {
            return c;
        } else if (f(a) * f(c) < 0) {
            b = c;
        } else {
            a = c;
        }

        if (fabs(c - prev_c) < eps) {
            break;
        }
        prev_c = c;
    } while ((b - a) / 2.0 > eps);

    return c;
}

double test_function(double x) {
    return cos(x / 2);
}

int main() {
    double a = 2.0, b = 4.0;
    double eps;
    double zero;

    for (int i = 1; i <= 8; i++) {
        eps = pow(0.1, (double)i);
        zero = findzero(test_function, a, b, eps);

        if (!isnan(zero)) {
            char format[100];
            sprintf(format, "Zero point: %%.%df (found with precision ε = %%.%df)\n", i, i);
            printf(format, zero, eps);
        }
    }

    return 0;
}
