def compute_sieve(n):
    sieve = [True] * (n + 1)
    sieve[0], sieve[1] = False, False  # 0 and 1 are not prime numbers

    for i in range(2, int(n**0.5) + 1):
        if sieve[i]:
            for j in range(i * i, n + 1, i):
                sieve[j] = False
    return sieve

def count_primes(sieve):
    return sum(1 for is_prime in sieve if is_prime)

def main():
    try:
        n = int(input("Podaj liczbe n do ktorej chcesz policzyc liczbe liczb pierwszych: "))
        if n < 2:
            print("n musi byc wieksze od 2")
            return

        sieve = compute_sieve(n)
        result = count_primes(sieve)
        print(f"Tyle liczb pierwszych {result} do liczby {n}")
    except ValueError:
        print("Blad")

if __name__ == "__main__":
    main()

