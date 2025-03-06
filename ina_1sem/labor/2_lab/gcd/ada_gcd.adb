with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;


procedure ada_gcd is

    function gcd(x: in Integer; y: in Integer) return Integer is
        a, b, c : Integer;
    begin
        a := x;
        b := y;
        c := 0;
    
        while b > 0 loop
            c := b;
            b := a mod b;
            a := c;
        end loop;
    
        return a;
    end gcd;

        x: Integer;
        y: Integer;
    begin
        Put_Line("Pierwsza liczba: ");
        Get(x);
    
        Put_Line("Druga liczba: ");
        Get(y);
    
        Put_Line("NWD to: " & Integer'Image(gcd(x, y)));
end ada_gcd;
