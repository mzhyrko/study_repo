import math

def isPrime(a):
    m = 3
    k = int(math.sqrt(a)) + 1

    if a == 2:
        return True
    elif a < 2 or a % 2 == 0:
        return False

    while m <= k:
        if a % m == 0:
            return False
        m = m + 2
        


    return True

def main():
    a = int(input("Wprowadz liczbe: "))

    if isPrime(a):
        print(f"{a} - jest liczba pierwsza")
    else:
        print(f"{a} - nie jest liczba pierwsza")

if __name__ == "__main__":
    main()

