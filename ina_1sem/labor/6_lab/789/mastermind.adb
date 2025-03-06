with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;

procedure Mastermind is
   type Code is array (1 .. 4) of Integer;
   type Code_List is array (Integer range <>) of Code;
   Max_Digit : constant Integer := 6;
   Max_Proposals : constant Integer := 1296; --6^4

   Possible_Codes : Code_List(1 .. Max_Proposals);
   Proposal : Code;
   Num_Possible : Integer := 0;

   Correct_Position : Integer;
   Correct_Digit_Wrong_Position : Integer;

   procedure Initialize_Possible_Codes is
      Index : Integer := 1;
   begin
      for D1 in 1 .. Max_Digit loop
         for D2 in 1 .. Max_Digit loop
            for D3 in 1 .. Max_Digit loop
               for D4 in 1 .. Max_Digit loop
                  Possible_Codes(Index) := (D1, D2, D3, D4);
                  Index := Index + 1;
               end loop;
            end loop;
         end loop;
      end loop;
      Num_Possible := Max_Proposals;
   end Initialize_Possible_Codes;

   procedure Compare_Codes(A, B : Code; Correct_Pos, Wrong_Pos : out Integer) is
      Used_A : array (1 .. 4) of Boolean := (others => False);
      Used_B : array (1 .. 4) of Boolean := (others => False);
   begin
      Correct_Pos := 0;
      Wrong_Pos := 0;

      for I in 1 .. 4 loop
         if A(I) = B(I) then
            Correct_Pos := Correct_Pos + 1;
            Used_A(I) := True;
            Used_B(I) := True;
         end if;
      end loop;

      for I in 1 .. 4 loop
         if not Used_A(I) then
            for J in 1 .. 4 loop
               if not Used_B(J) and A(I) = B(J) then
                  Wrong_Pos := Wrong_Pos + 1;
                  Used_B(J) := True;
                  exit;
               end if;
            end loop;
         end if;
      end loop;
   end Compare_Codes;

   procedure Filter_Possible_Codes(Last_Proposal : Code; Pos, WPos : Integer) is
      New_List : Code_List(1 .. Max_Proposals);
      New_Count : Integer := 0;
      C_Pos, C_WPos : Integer;
   begin
      for I in 1 .. Num_Possible loop
         Compare_Codes(Possible_Codes(I), Last_Proposal, C_Pos, C_WPos);
         if C_Pos = Pos and C_WPos = WPos then
            New_Count := New_Count + 1;
            New_List(New_Count) := Possible_Codes(I);
         end if;
      end loop;

      Possible_Codes(1 .. New_Count) := New_List(1 .. New_Count);
      Num_Possible := New_Count;
   end Filter_Possible_Codes;

begin
   Put_Line("Mastermind");
   Initialize_Possible_Codes;

   loop
      Proposal := Possible_Codes(1);
      Put("Proposal: ");
      for I in Proposal'Range loop
         Put(Integer'Image(Proposal(I)));
      end loop;
      New_Line;

      Put("Correct positions: ");
      Get(Correct_Position);
      Put("Correct digits in wrong positions: ");
      Get(Correct_Digit_Wrong_Position);

      if Correct_Position = 4 then
         Put_Line("I win!");
         exit;
      end if;

      Filter_Possible_Codes(Proposal, Correct_Position, Correct_Digit_Wrong_Position);

      if Num_Possible = 0 then
         Put_Line("You are cheating!");
         exit;
      end if;
   end loop;
end Mastermind;

