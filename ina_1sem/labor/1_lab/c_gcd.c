#include <stdio.h>
#include <limits.h>
int main(){
	
	int a;
	int b;
	int c = 0;

	printf("Napisz pierwsza liczbe: " );
	scanf("%d", &a);
	printf("Napisz druga liczbe: ");
	scanf("%d", &b);
    
    if(a == INT_MIN)  
        a = a%b;
    else if(b == INT_MIN)
        b = b%a;
    else if( b < 0 )
        b=-b;

	while(b > 0){
		c = b;
		b = a % b;
		a = c;
		
	}
	printf("NWD to : %d\n", a);
	return 0;
}
