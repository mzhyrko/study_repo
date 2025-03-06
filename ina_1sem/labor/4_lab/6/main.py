import sys
from totient import Totient

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Użycie: python program <liczby...>")
        sys.exit(1)


    results = []
    for arg in sys.argv[1:]:
        try:
            number = int(arg)
            results.append(f"totient({number}) = {Totient(number)}")
        except ValueError:
            results.append(f"Nieprawidłowa liczba: {arg}")

    print("\n".join(results))

