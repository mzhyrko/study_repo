with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Numerics.Elementary_Functions; use Ada.Numerics.Elementary_Functions;

procedure ada_fact is

    procedure primeFactorization(n: in out Integer) is
        count : Integer;
        i : Integer := 3;
    begin
        count := 0;
        while (n mod 2 = 0) loop
            count := count + 1;
            n := n / 2;
        end loop;

        if (count > 0) then
            Put("2^" & Integer'Image(count)(2..Integer'Image(count)'Last));
            if (n > 1) then
                Put("*");
            end if;
        end if;

        while i ** 2 <= n loop
            count := 0;
            while (n mod i = 0) loop
                count := count + 1;
                n := n / i;
            end loop;

            if (count > 0) then
                Put(Integer'Image(i)(2..Integer'Image(i)'Last) & "^" & Integer'Image(count)(2..Integer'Image(count)'Last));
                if (n > 1) then
                    Put("*");
                end if;
            end if;
            i := i + 2;
        end loop;

        if (n > 2) then
            Put(Integer'Image(n)(2..Integer'Image(n)'Last));
        end if;

    end primeFactorization;

    num: Integer;
begin
    Put("Napisz liczbe: ");
    Get(num);
    primeFactorization(num);
    New_Line;
end ada_fact;

