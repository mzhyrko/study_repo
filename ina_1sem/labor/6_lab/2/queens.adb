with Ada.Command_Line;
with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Containers.Vectors;

procedure N_Queens is
   type Board is array (Positive range <>) of Positive;
   type Int_Vector is array (Positive range <>) of Integer;

   procedure Print_Board(Solution : in Board) is
   begin
      for I in Solution'Range loop
         Put(Integer(Solution(I)));
         if I /= Solution'Last then
            Put(" ");
         end if;
      end loop;
      New_Line;
   end Print_Board;

   -- Funkcja sprawdzająca, czy permutacja jest rozwiązaniem
   function Is_Solution(Perm : in Board) return Boolean is
   begin
      for I in Perm'Range loop
         for J in I + 1 .. Perm'Last loop
            if (Perm(J) = Perm(I) + (J - I)) or else (Perm(J) = Perm(I) - (J - I)) then
               return False;
            end if;
         end loop;
      end loop;
      return True;
   end Is_Solution;

   function Next_Permutation(Perm : in out Board) return Boolean is
      K, L : Integer;
   begin
      K := Perm'Length - 1;
      while K > 0 and then Perm(K) >= Perm(K + 1) loop
         K := K - 1;
      end loop;
      if K = 0 then
         return False;
      end if;

      L := Perm'Length;
      while Perm(L) <= Perm(K) loop
         L := L - 1;
      end loop;

      declare
         Temp : Integer := Perm(K);
      begin
         Perm(K) := Perm(L);
         Perm(L) := Temp;
      end;

      declare
         Left  : Integer := K + 1;
         Right : Integer := Perm'Length;
         Temp  : Integer;
      begin
         while Left < Right loop
            Temp := Perm(Left);
            Perm(Left) := Perm(Right);
            Perm(Right) := Temp;
            Left := Left + 1;
            Right := Right - 1;
         end loop;
      end;

      return True;
   end Next_Permutation;

begin
   declare
      N : Positive := Positive'Value(Ada.Command_Line.Argument(1));
      Solutions_Count : Natural := 0;
      Board_Array : Board(1 .. N);
   begin
      for I in Board_Array'Range loop
         Board_Array(I) := I;
      end loop;

      loop
         if Is_Solution(Board_Array) then
            Print_Board(Board_Array);
            Solutions_Count := Solutions_Count + 1;
         end if;

         exit when not Next_Permutation(Board_Array);
      end loop;

      Put("Number of solutions: ");
      Put(Solutions_Count);
      New_Line;
   end;

end N_Queens;

