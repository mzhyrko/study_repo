import sys

def print_solution(coins, count):
    for i in range(len(coins)):
        if count[i] > 0:
            print(f"{count[i]} x {coins[i]}")

def coinchange(coins, amount):
    if amount < 0:
        print(f"{amount} ==> Invalid amount (must be non-negative)!")
        return -1

    dp = [float('inf')] * (amount + 1)
    track = [-1] * (amount + 1)
    dp[0] = 0

    for i in range(len(coins)):
        if coins[i] <= 0:
            print(f"Error: Coin value {coins[i]} is invalid (must be positive).")
            return -1
        for j in range(coins[i], amount + 1):
            if dp[j - coins[i]] != float('inf') and dp[j] > dp[j - coins[i]] + 1:
                dp[j] = dp[j - coins[i]] + 1
                track[j] = i

    if dp[amount] == float('inf'):
        return -1

    count = [0] * len(coins)
    current = amount
    while current > 0:
        count[track[current]] += 1
        current -= coins[track[current]]

    print(f"{amount} ==> {dp[amount]}")
    print_solution(coins, count)

    return dp[amount]

def read_coins_from_file(file_path):
    coins = []
    try:
        with open(file_path, 'r') as file:
            first_line = file.readline().strip()
            if not first_line.isdigit():
                raise ValueError("First line of the file must be an integer (number of coins).")
            coin_count = int(first_line)
            for _ in range(coin_count):
                line = file.readline().strip()
                if not line.isdigit():
                    raise ValueError("Coin values must be positive integers.")
                coin = int(line)
                if coin <= 0:
                    raise ValueError("Coin values must be positive integers.")
                coins.append(coin)
    except FileNotFoundError:
        print(f"Error: File '{file_path}' not found.", file=sys.stderr)
        sys.exit(1)
    except ValueError as e:
        print(f"Error in file '{file_path}': {e}", file=sys.stderr)
        sys.exit(1)
    except Exception as e:
        print(f"Unexpected error reading file '{file_path}': {e}", file=sys.stderr)
        sys.exit(1)
    return coins

def main():
    if len(sys.argv) < 3:
        print(f"Usage: {sys.argv[0]} <coin_file> <amount1> <amount2> ...", file=sys.stderr)
        sys.exit(1)

    coin_file = sys.argv[1]
    amounts = []
    for arg in sys.argv[2:]:
        if not arg.isdigit() or int(arg) < 0:
            print(f"Error: Invalid amount '{arg}'. Amounts must be non-negative integers.", file=sys.stderr)
            sys.exit(1)
        amounts.append(int(arg))

    coins = read_coins_from_file(coin_file)

    for amount in amounts:
        if coinchange(coins, amount) == -1:
            print(f"{amount} ==> No solution!")

if __name__ == "__main__":
    main()
