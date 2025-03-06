from math import cos

def find_zero(f, a, b, eps):
    if f(a) * f(b) >= 0:
        print("Error: the function must have different signs at the ends of the interval.")
        return None

    c, prev_c = a, a
    while (b - a) / 2.0 > eps:
        c = (a + b) / 2.0
        if f(c) == 0.0:
            return c
        elif f(a) * f(c) < 0:
            b = c
        else:
            a = c

        if abs(c - prev_c) < eps:
            break
        prev_c = c

    return c

def test_function(x):
    return cos(x / 2)

def main():
    a, b = 2.0, 4.0
    for i in range(1, 9):
        eps = 10 ** -i
        zero = find_zero(test_function, a, b, eps)
        if zero is not None:
            format_string = f"Zero point: {{:.{i}f}} (found with precision ε = {{:.{i}f}})"
            print(format_string.format(zero, eps))

if __name__ == "__main__":
    main()
