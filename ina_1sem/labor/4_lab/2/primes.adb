with Ada.Numerics.Elementary_Functions;
use Ada.Numerics.Elementary_Functions;

package body Primes is

    function IsPrime (n : Positive) return Boolean is
    begin
        if n < 2 then
            return False;
        end if;
        for i in 2 .. Integer (Sqrt (Float (n))) loop
            if n mod i = 0 then
                return False;
            end if;
        end loop;
        return True;
    end IsPrime;

    function PrimeNumbers (n : Positive) return Positive is
        Count : Natural := 0;
    begin
        for i in 2 .. n loop
            if IsPrime(i) then
                Count := Count + 1;
            end if;
        end loop;
        return Positive(Count);
    end PrimeNumbers;

    function Prime (n : Positive) return Positive is
        Count     : Natural := 0;
        Candidate : Positive := 2;
    begin
        while Count < n loop
            if IsPrime(Candidate) then
                Count := Count + 1;
            end if;
            if Count < n then
                Candidate := Candidate + 1;
            end if;
        end loop;
        return Candidate;
    end Prime;

end Primes;

