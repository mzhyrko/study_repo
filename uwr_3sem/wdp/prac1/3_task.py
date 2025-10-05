def krzyzyk(n):
    size = 3 * n
    for i in range(size):
        for j in range(size):
            if (n <= i < 2 * n) or (n <= j < 2 * n):
                print("*", end="")
            else:
                print(" ", end="")
        print()

#main
krzyzyk(4)