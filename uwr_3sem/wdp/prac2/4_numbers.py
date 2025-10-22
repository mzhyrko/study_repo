from duze_cyfry import daj_cyfre

def wypisz_duza_liczbe(liczba):
    cyfry = [int(c) for c in str(liczba)]
    
    cyfry_wyswietl = [daj_cyfre(cyfra) for cyfra in cyfry]
    
    for i in range(5): 
        linia = ''
        for j in range(len(cyfry)):
            linia += cyfry_wyswietl[j][i] + ' '  
        print(linia.rstrip())  
    
    
    print("\nUproszczona wersja (pionowo):")
    for cyfra in cyfry_wyswietl:
        for linia in cyfra:
            print(linia)
        print()  
    

wypisz_duza_liczbe(12345)