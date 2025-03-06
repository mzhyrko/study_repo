import math

def estimate_limit(n):
    if n < 6:
        return 15
    return int(n * math.log(n) + n * math.log(math.log(n)))

def compute_sieve(limit):
    sieve = [True] * (limit + 1)
    sieve[0], sieve[1] = False, False
    for i in range(2, int(math.sqrt(limit)) + 1):
        if sieve[i]:
            for j in range(i * i, limit + 1, i):
                sieve[j] = False
    return sieve

def find_nth_prime(n):
    limit = estimate_limit(n)
    sieve = compute_sieve(limit)
    count = 0
    for i, is_prime in enumerate(sieve):
        if is_prime:
            count += 1
            if count == n:
                return i
    return None

def main():
    try:
        n = int(input("Podaj liczbe n, aby znalezc n-ta liczbe pierwsza: "))
        if n < 1:
            print("Podaj liczbe calkowita wieksza od 0.")
            return
        result = find_nth_prime(n)
        print(f"{n}-ta liczba pierwsza to: {result}")
    except ValueError:
        print("Niepoprawne dane. Podaj liczbe calkowita.")

if __name__ == "__main__":
    main()
