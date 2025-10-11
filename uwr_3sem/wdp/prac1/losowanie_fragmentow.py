from random import choice

fragmenty = "sia je ta da pra wie nie ce ca be ba bu ko rolo waje wsie fija kura kika fra fiu fu ra ro chry kawa kwa waka tra cny dze rze rzy grzy wsze ja na ma kre dy pu pi bi gra fra dro kila laki juki fika miki fiki we wa wu ku ka ra cza cze czu czte siu by bry bre bru gi gida gafa fago zy za zi zie zimy cima cia ciu dziu".split()

def losuj_fragment():
    return choice(fragmenty)

def losuj_haslo(n):
    while True:
        haslo = ""
        while len(haslo) < n:
            haslo += losuj_fragment()
        if len(haslo) == n:
            return haslo

#main
print("Hasła długości 8:")
for _ in range(100):
     print(losuj_haslo(8))

print("\nHasła długości 12:")
for _ in range(100):
     print(losuj_haslo(12))
