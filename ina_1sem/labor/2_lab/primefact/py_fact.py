def primeFactorization(n):
    count = 0
    
    while n % 2 == 0:
        count = count + 1;
        n //= 2;
    if count > 0:
        print(f"2^{count}", end="")
        if n > 1:
            print("*", end="")

    for i in range(3, int(n**0.5) + 1, 2):
        count = 0
        while n % i == 0:
            count = count + 1;
            n //= i;
        if count > 0:
            if count == 1:
                print(f"{i}", end="")
            else:
                print(f"{i}^{count}", end="")
            if n > 1:
                print("*", end="")

    if n > 2:
        print(n)

def main():
    num = int(input("Enter a positive integer: "))
    if num <= 0:
        return 0;
    else:
        primeFactorization(num)

if (__name__ == "__main__"):
    main()

