with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;

procedure N_Queens is

   type Board_Array is array (Positive range <>) of Integer;

   procedure Solve_N_Queens (N : Positive) is
      Position    : Board_Array (1 .. N);
      Bije_Wiersz : array (1 .. N) of Boolean := (others => False);
      Bije_Przek1 : array (1 .. 2 * N) of Boolean := (others => False);
      Bije_Przek2 : array (1 .. 2 * N) of Boolean := (others => False);
      Solution_Count : Natural := 0;

      procedure Print_Solution is
      begin
         for I in Position'Range loop
            Put(Position(I)'Img & " ");
         end loop;
         New_Line;
      end Print_Solution;

      procedure Place_Queen (I : Positive) is
      begin
         for J in 1 .. N loop
            if not (Bije_Wiersz(J) or Bije_Przek1(I + J) or Bije_Przek2(I - J + N)) then
               -- Place queen
               Position(I) := J;
               Bije_Wiersz(J) := True;
               Bije_Przek1(I + J) := True;
               Bije_Przek2(I - J + N) := True;

               if I < N then
                  Place_Queen(I + 1);
               else
                  -- Print solution
                  Print_Solution;
                  Solution_Count := Solution_Count + 1;
               end if;

               -- Backtrack
               Bije_Wiersz(J) := False;
               Bije_Przek1(I + J) := False;
               Bije_Przek2(I - J + N) := False;
            end if;
         end loop;
      end Place_Queen;

   begin
      Place_Queen(1);
      Put_Line("\nLiczba rozwiazan: " & Solution_Count'Img);
   end Solve_N_Queens;

   N : Positive;

begin
   Put_Line("Podaj liczbe hetmanow (n):");
   Get(N);
   Solve_N_Queens(N);
end N_Queens;

