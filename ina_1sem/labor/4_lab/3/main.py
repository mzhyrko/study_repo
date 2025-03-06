import sys
from primes import PrimeNumbers, Prime, IsPrime

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Użycie: python program <komenda> <liczba>")
        sys.exit(1)

    command = sys.argv[1]
    try:
        number = int(sys.argv[2])
    except ValueError:
        print("Podaj prawidłową liczbę.")
        sys.exit(1)

    if command == "pn":
        print(PrimeNumbers(number))
    elif command == "pr":
        try:
            print(Prime(number))
        except ValueError as e:
            print(e)
    elif command == "ip":
        print(IsPrime(number))
    else:
        print("Nieznana komenda. Użyj 'pn', 'pr' lub 'ip'.")
        sys.exit(1)

