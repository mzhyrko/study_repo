import math

def main():
    print("Napisz wspolczynniki do rownania ax*x + bx + c = 0:")
    a = float(input("a = "))
    b = float(input("b = "))
    c = float(input("c = "))

    discr = b**2 - (4 * a * c); 

    if discr < 0:
        print("Nie ma rozwiazan")
    elif discr > 0: 
        print(f"Rownanie ma 2 rozwiazania: {(-b + math.sqrt(discr)) / (2 * a)} i {(-b - math.sqrt(discr)) / (2 * a)}")
    elif discr == 0:
        print(f"Rownanie ma 1 rozwiazanie: {(-b) / (2 * a)}")  

if __name__ == "__main__":
    main()

