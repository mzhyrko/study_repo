with Ada.Text_IO; use Ada.Text_IO;
with Ada.Numerics.Elementary_Functions; use Ada.Numerics.Elementary_Functions;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;


procedure ada_ninkpol is 
    n: Integer;
    k: Integer;
    kop: Integer := 0;
    rev: Integer := 0;

begin
        Put_Line("Wprowadz liczbe n i jej podstawe k");
        Put("n = ");
        Get(n);
        Put("k = ");
        Get(k);

        kop := n;

        while kop > 0 loop
            rev := (rev * k) + (kop mod k);
            kop := kop / k;
        end loop;

        if n = rev then 
            Put_Line("Liczba jest polindromem w systemie k");
        elsif n /= rev then
            Put_Line("Liczba nie jest polindromem w systemie k");
        end if;

end ada_ninkpol;
