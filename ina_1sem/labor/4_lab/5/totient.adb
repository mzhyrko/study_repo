with Ada.Numerics.Elementary_Functions; use Ada.Numerics.Elementary_Functions;

package body Totient is

    --function GCD (a, b : Positive) return Positive is
    --begin
        --if b = 0 then
            --return a;
        --else
            --return GCD(b, a mod b);
        --end if;
    --end GCD;

    function Totient (n : Positive) return Positive is
        Result : Positive := n;
        Temp   : Positive := n;
        I      : Positive := 2;
    begin
        while I * I <= Temp loop
            if Temp mod I = 0 then
                while Temp mod I = 0 loop
                    Temp := Temp / I;
                end loop;
                Result := Result - Result / I;
            end if;
            I := I + 1;
        end loop;

        if Temp > 1 then
            Result := Result - Result / Temp;
        end if;

        return Result;
    end Totient;

end Totient;

