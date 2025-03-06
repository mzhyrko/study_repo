def PrimeNumbers(n):
    if n < 2:
        return 0
    primes = [True] * (n + 1)
    primes[0] = primes[1] = False
    for i in range(2, int(n**0.5) + 1):
        if primes[i]:
            for j in range(i * i, n + 1, i):
                primes[j] = False
    return sum(primes)

def Prime(n):
    if n < 1:
        raise ValueError("n musi być większe od 0.")
    primes = []
    candidate = 2
    while len(primes) < n:
        if IsPrime(candidate):
            primes.append(candidate)
        candidate += 1
    return primes[-1]

def IsPrime(num):
    if num < 2:
        return False
    for i in range(2, int(num**0.5) + 1):
        if num % i == 0:
            return False
    return True

