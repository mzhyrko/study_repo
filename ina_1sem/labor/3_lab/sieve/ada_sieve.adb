with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;

procedure PrimeNumbers is
    function PrimeNumbers (n : Natural) return Natural is
        type Sieve_Type is array (Positive range <>) of Boolean;
        sieve : Sieve_Type(2 .. n);

        procedure ComputeSieve (s : in out Sieve_Type) is
            j : Natural;
        begin
            for i in s'Range loop
                s(i) := True;
            end loop;

            for i in s'Range loop
                if s(i) then
                    j := i + i;
                    while j <= n loop
                        s(j) := False;
                        j := j + i;
                    end loop;
                end if;
            end loop;
        end ComputeSieve;

        function CountPrimes(s : Sieve_Type) return Natural is
            count : Natural := 0;
        begin
            for i in s'Range loop
                if s(i) then
                    count := count + 1;
                end if;
            end loop;
            return count;
        end CountPrimes;

    begin
        if n < 2 then
            Put_Line("Nieprawidlowa wartosc n");
            return 0;
        else
            ComputeSieve(sieve);
            return CountPrimes(sieve);
        end if;
    end PrimeNumbers;

begin
    Put("Podaj liczbe n do ktorej chcesz policzyc liczbe liczb pierwszych: ");
    declare
        n : Natural;
        result : Natural;
    begin
        Get(n);
        result := PrimeNumbers(n);
        Put_Line("Istnieje " & Natural'Image(result) & " liczb pierwszych do " & Natural'Image(n) & ".");
    exception
        when others =>
            Put_Line("Nieprawidlowe dane wejsciowe.");
    end;
end PrimeNumbers;

