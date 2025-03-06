with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;

procedure Ada_Newton is

   function Newton (N, K : Integer) return Integer is
      type Int_Array is array (Integer range <>, Integer range <>) of Integer;
      binom_array : Int_Array (0 .. N, 0 .. N);
   begin
      for I in 0 .. N loop
         for J in 0 .. I loop
            if J = 0 or else J = I then
               binom_array(I, J) := 1;
            else
               binom_array(I, J) := binom_array(I - 1, J - 1) + binom_array(I - 1, J);
            end if;
         end loop;
      end loop;
      return binom_array(N, K);
   exception
      when others =>
         Put_Line("Błąd podczas obliczeń.");
         return -1;
   end Newton;

   N, K : Integer;
begin
   Put("Podaj n i k: ");
   Get(N);
   Get(K);
   if K > N or else K < 0 then
      Put_Line("Niepoprawne wartości n i k.");
   else
      Put("Współczynnik dwumianowy wynosi: ");
      Put(Newton(N, K), Width => 0);
      New_Line;
   end if;
end Ada_Newton;
