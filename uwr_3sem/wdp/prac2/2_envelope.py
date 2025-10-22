def envelope(n):
    size = 2 * n + 1
    for row in range(size):
        line = ''
        for col in range(size):
            if (row == 0 or col == 0) or (row == size - 1 or col == size - 1) or (row == col or row + col == size - 1):
                line += '*'
            else:
                line += ' '
        print(line)

envelope(1)