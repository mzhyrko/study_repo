import math

def main():
    print("Napisz liczbe n i jej podstawe k")

    n = int(input("n = "))
    k = int(input("k = "))
    kop = int
    rev = int

    kop = n;
    rev = 0;

    while kop > 0:
        rev = (rev * k)+(kop % k);
        kop = kop // k;

    if n == rev:
        print("Liczba jest polindromem")
    elif n != rev:
        print("Liczba nie jest polindromem")

if __name__ == "__main__":
    main()


