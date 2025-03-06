def gcd(a, b):
    
    a = abs(a)
    b = abs(b)
    c = int()

    while b > 0:
        c = b
        b = a % b
        a = c

    return a

def main():
    a = int(input("Pierwsza liczba: "))
    b = int(input("Druga liczba: "))

    print(f"NWD to: {gcd(a, b)}")

if __name__ == "__main__":
    main()

