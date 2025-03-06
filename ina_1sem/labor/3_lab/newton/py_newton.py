def newton(n, k):
    if k < 0 or k > n:
        return 0
    if k == 0 or k == n:
        return 1
    array = [[0 for _ in range(k + 1)] for _ in range(n + 1)]
    for i in range(n + 1):
        for j in range(min(i, k) + 1):
            if j == 0 or j == i:
                array[i][j] = 1
            else:
                array[i][j] = array[i - 1][j - 1] + array[i - 1][j]
    return array[n][k]

def main():
    try:
        n_str, k_str = input("Podaj n i k: ").split()
        n = int(n_str)
        k = int(k_str)
        result = newton(n, k)
        print("Współczynnik dwumianowy wynosi:", result)
    except ValueError:
        print("Błędne dane wejściowe. Podaj liczby całkowite.")
    except Exception as e:
        print(f"Wystąpił błąd: {e}")

if __name__ == "__main__":
    main()

