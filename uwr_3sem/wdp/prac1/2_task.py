from math import log, floor

def silnia(n):
    if n == 0 or n == 1:
        return 1
    
    wynik = 1
    for i in range(2, n + 1):
        wynik *= i
    return wynik

def get_cyfra_form(num):
    if num == 1:
        return "cyfrę"
    last_two_digits = num % 100
    if last_two_digits in [12, 13, 14]:
        return "cyfr"
    last_digit = num % 10
    if last_digit in [2, 3, 4]:
        return "cyfry"
    return "cyfr"

# main
for n in range(4, 101):
    factorial = silnia(n)
    num_digits = len(str(factorial))
    num_digits_alternative = floor(log(factorial) + 1) #alternative metod of counting digits
    cyfra_form = get_cyfra_form(num_digits)
    print(f"{n}! ma {num_digits} {cyfra_form}")