from itertools import product

def generate_codes(max_digit, code_length):
    """Generate all possible codes as Cartesian product."""
    return list(product(range(1, max_digit + 1), repeat=code_length))

def matches_feedback(code, guess, correct_pos, correct_digit):
    """Check if a code matches the given feedback."""
    used_code = [False] * len(code)
    used_guess = [False] * len(guess)

    pos_match = 0
    digit_match = 0

    # Check correct positions
    for i in range(len(code)):
        if code[i] == guess[i]:
            pos_match += 1
            used_code[i] = True
            used_guess[i] = True

    # Check correct digits in wrong positions
    for i in range(len(guess)):
        if not used_guess[i]:
            for j in range(len(code)):
                if not used_code[j] and guess[i] == code[j]:
                    digit_match += 1
                    used_code[j] = True
                    break

    return pos_match == correct_pos and digit_match == correct_digit

def mastermind(max_digit=6, code_length=4):
    """Mastermind game solver."""
    codes = generate_codes(max_digit, code_length)
    possible = [True] * len(codes)

    print(f"Think of a {code_length}-digit code using numbers 1 to {max_digit}.")

    for attempt in range(1, 9):
        # Find the first possible code
        for i, is_possible in enumerate(possible):
            if is_possible:
                guess = codes[i]
                break
        else:
            print("You are cheating")
            return

        print(f"{attempt}: {' '.join(map(str, guess))}?")

        try:
            correct_pos = int(input("Correct positions: "))
            correct_digit = int(input("Correct digits in wrong positions: "))
        except ValueError:
            print("Invalid input. Please enter integers.")
            continue

        if correct_pos + correct_digit > code_length or correct_pos < 0 or correct_digit < 0:
            print("Invalid feedback. Please check your input.")
            continue

        if correct_pos == code_length:
            print("I won! The code is correct.")
            return

        # Eliminate impossible codes
        for i in range(len(codes)):
            if possible[i] and not matches_feedback(codes[i], guess, correct_pos, correct_digit):
                possible[i] = False

    print("I couldn't guess the code within 8 attempts.")

if __name__ == "__main__":
    mastermind(max_digit=6, code_length=4)

