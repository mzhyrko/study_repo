with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Numerics; -- Moduł dla funkcji matematycznych
with Ada.Numerics.Elementary_Functions; use Ada.Numerics.Elementary_Functions;

procedure Nth_Prime is
    type Boolean_Array is array (Positive range <>) of Boolean; -- Definicja dynamicznego typu tablicowego

    function Estimate_Limit (N : Natural) return Natural is
    begin
        if N < 6 then
            return 15;
        else
            return Natural (Float (N) * (Log(Float(N)) + Log(Log(Float(N)))));
        end if;
    end Estimate_Limit;

    procedure Compute_Sieve (S : in out Boolean_Array; Limit : Natural) is
    begin
        for I in S'Range loop
            S(I) := True;
        end loop;

        for I in 2 .. Natural(Sqrt(Float(Limit))) loop
            if S(I) then
                for J in I * I .. Limit loop
                    if J mod I = 0 then
                        S(J) := False;
                    end if;
                end loop;
            end if;
        end loop;
    end Compute_Sieve;

    function Find_Nth_Prime (N : Natural) return Natural is
        Limit : Natural := Estimate_Limit(N);
        S : Boolean_Array(2 .. Limit);
        Count : Natural := 0;
    begin
        Compute_Sieve(S, Limit);

        for I in S'Range loop
            if S(I) then
                Count := Count + 1;
                if Count = N then
                    return I;
                end if;
            end if;
        end loop;

        return 0;
    end Find_Nth_Prime;

    N, Result : Natural;
begin
    Put("Podaj liczbe n, aby znalezc n-ta liczbe pierwsza: ");
    Get(N);
    Result := Find_Nth_Prime(N);
    Put_Line("N-ta liczba pierwsza to: " & Natural'Image(Result));
end Nth_Prime;

