with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;

procedure gcd is
	a: Integer;
	b: Integer;
	c: Integer := 0;
begin
	Put_Line("Pierwsza liczba: ");
	Get(a);
	Put_Line("Druga liczba: ");
	Get(b);
    
    if a = -2147483648 then 
        a := 2147483647;
    elsif b = -2147483648 then
        b := 2147483647;
    end if; 

    if a < 0 then
        a := -a;
    elsif b < 0 then 
        b := -b;
    end if;

	while b > 0 loop
		
		c := b;
		b := a mod b;
		a := c;
	
	end loop;
	
	Put_Line("NWD to: " & a'Image);
end gcd;
