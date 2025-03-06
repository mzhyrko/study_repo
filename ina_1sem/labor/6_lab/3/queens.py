def is_valid_permutation(arr):
    n = len(arr)
    for i in range(n):
        for j in range(i + 1, n):
            if abs(arr[i] - arr[j]) == abs(i - j):
                return False
    return True

def generate_permutations(n):
    arr = list(range(n))
    solutions = 0

    while True:
        if is_valid_permutation(arr):
            print([x + 1 for x in arr])
            solutions += 1

        i = n - 2
        while i >= 0 and arr[i] > arr[i + 1]:
            i -= 1

        if i < 0:
            break

        j = n - 1
        while arr[j] <= arr[i]:
            j -= 1

        arr[i], arr[j] = arr[j], arr[i]
        arr[i + 1:] = reversed(arr[i + 1:])

    return solutions

def main():
    n = int(input("Enter the value of n: "))
    solutions = generate_permutations(n)
    print(f"Number of solutions: {solutions}")

if __name__ == "__main__":
    main()

