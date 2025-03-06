# include <stdio.h>
# include <math.h>

int main(){
	float a = 0;
	float b = 0;
	float c = 0;

	float discr = 0;

	printf("Napisz wspolczynniki do rownania kwadratowego ax*x + bx + c = 0: \n");
	printf("a = ");
	scanf("%f", &a);
	printf("\n b = ");
	scanf("%f", &b);
	printf("\n c = ");
	scanf("%f", &c);	
	
	discr = (b*b)-(4*a*c);

	if(discr < 0){
		printf("Nie ma rozwizan");
		return 0;
	} else if (discr == 0){
		printf("Rownanie ma 1 roziazanie: %f", (-b)/(2*a));
		return 0;
	} else if (discr > 0){
		printf("Rownanie ma 2 rozwiazania: %f , %f \n", (-b+sqrt(discr))/(2*a), (-b-sqrt(discr))/(2*a));
		return 0;
	}
	return 0;
}
