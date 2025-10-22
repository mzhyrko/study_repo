def szachownica(n, k):
    for big_row in range(2 * n):
        for small_row in range(k):
            line = ''
            for big_col in range(2 * n):
                for small_col in range(k):
                    if (big_row + big_col) % 2 == 0:
                        line += '#'
                    else:
                        line += ' '
            print(line)

szachownica(4, 2)