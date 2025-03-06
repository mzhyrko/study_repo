with Ada.Text_IO; use Ada.Text_IO;
with Ada.Command_Line; use Ada.Command_Line;
with Primes;

procedure Program is
    Command : String := Argument(1);
    Number  : Positive;
begin
    if Argument_Count /= 2 then
        Put_Line("Użycie: ./program <komenda> <liczba>");
        return;
    end if;

    begin
        Number := Positive'Value(Argument(2));
    exception
        when others =>
            Put_Line("Podaj prawidłową liczbę.");
            return;
    end;

    if Command = "pn" then
        Put_Line(Positive'Image(Primes.PrimeNumbers(Number)));
    elsif Command = "pr" then
        Put_Line(Positive'Image(Primes.Prime(Number)));
    elsif Command = "ip" then
        Put_Line(Boolean'Image(Primes.IsPrime(Number)));
    else
        Put_Line("Nieznana komenda. Użyj 'pn', 'pr' lub 'ip'.");
    end if;
end Program;

