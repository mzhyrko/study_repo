package Primes is
    -- Funkcja zwracająca liczbę liczb pierwszych mniejszych lub równych n
    function PrimeNumbers (n : Positive) return Positive;

    -- Funkcja zwracająca n-tą liczbę pierwszą
    function Prime (n : Positive) return Positive;

    -- Funkcja sprawdzająca, czy podana liczba jest liczbą pierwszą
    function IsPrime (n : Positive) return Boolean;

private
    -- Typy i funkcje pomocnicze są ukryte
end Primes;

