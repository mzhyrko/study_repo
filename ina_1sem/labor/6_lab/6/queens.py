import sys

def hetmani(n):
    position = [0] * n
    bije_wiersz = [False] * n
    bije_przek1 = [False] * (2 * n)
    bije_przek2 = [False] * (2 * n)

    solutions = []

    def ustaw(i):
        for j in range(n):
            if not (bije_wiersz[j] or bije_przek1[i + j] or bije_przek2[i - j + n - 1]):
                position[i] = j
                bije_wiersz[j] = bije_przek1[i + j] = bije_przek2[i - j + n - 1] = True

                if i < n - 1:
                    ustaw(i + 1)
                else:
                    solutions.append(position[:])

                position[i] = 0
                bije_wiersz[j] = bije_przek1[i + j] = bije_przek2[i - j + n - 1] = False

    ustaw(0)
    return solutions

def main():
    if len(sys.argv) != 2:
        print("Użycie: python n_queens_solver.py <n>")
        return

    try:
        n = int(sys.argv[1])
        if n <= 0:
            raise ValueError
    except ValueError:
        print("Podaj poprawną liczbę całkowitą większą od 0.")
        return

    solutions = hetmani(n)
    print(f"Znalezione rozwiązania dla n={n}:")
    for solution in solutions:
        print(solution)

    print(f"\nLiczba rozwiązań: {len(solutions)}")

if __name__ == "__main__":
    main()

