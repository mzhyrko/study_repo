with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Numerics.Elementary_Functions; use Ada.Numerics.Elementary_Functions;

procedure ada_isprime is

    function isPrime(a: in Integer) return Boolean is
        m, k : Integer;
    begin
        m := 3;     
        k := Integer(Sqrt(Float(a)));
        
        if a = 2 then 
            return True;
        elsif a < 2 then
            return False;
        elsif a mod 2 = 0 then 
            return False;
        end if;

        while((m <= k) and (a mod m /= 0)) loop
            m := m+2;        
            if(m > k) then
                return True;
            end if;
        end loop;

        return False;
    end isPrime;

        a: Integer;
    begin
        Put_Line("Pierwsza liczba: ");
        Get(a);
 
        if isPrime(a) then
            Put_Line(a'Image & " - jest liczba pierwsza");
        else   
            Put_Line(a'Image & " - nie jest liczba pierwsza");
        end if;

end ada_isprime;
