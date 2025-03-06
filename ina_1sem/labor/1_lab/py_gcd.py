def main():
    a = int(input("Pierwsza liczba: "))
    b = int(input("Druga liczba: "))
    c = int
   
    if a < 0:
        a = -a;
    elif b < 0:
        b = -b;

    while b > 0 :
        c = b;
        b = a%b;
        a=c;

    print(f"Nwd to : {a} ")

if __name__ == "__main__":
    main()
