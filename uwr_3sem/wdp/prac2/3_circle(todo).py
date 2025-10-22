import math

def kolko(n):
    r = n / 2
    for y in range(n):
        for x in range(n):
            d = math.sqrt((x - r + 0.5) ** 2 + (y - r + 0.5) ** 2)
            if r - 0.5 <= d <= r + 0.5:
                print("#", end="")
            else:
                print(" ", end="")
        print()

def balwanek():
    kolko(7)
    kolko(9)
    kolko(13)

balwanek()