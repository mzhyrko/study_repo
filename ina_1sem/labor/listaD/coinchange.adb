with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Command_Line; use Ada.Command_Line;

procedure coinchange is
    Max_Coins : constant := 1000;
    type Coin_Array is array (1 .. Max_Coins) of Integer;

    procedure Print_Solution(Coins : Coin_Array; Count : Coin_Array; Coin_Count : Natural) is
    begin
        for I in 1 .. Coin_Count loop
            if Count(I) > 0 then
                Put_Line(Integer'Image(Count(I)) & " x " & Integer'Image(Coins(I)));
            end if;
        end loop;
    end Print_Solution;

    function Coin_Change(Coins : Coin_Array; Coin_Count : Natural; Amount : Integer) return Integer is
        DP : array (0 .. Amount) of Integer := (0 => 0, others => Integer'Last);
        Track : array (0 .. Amount) of Integer := (others => -1);
        Count : Coin_Array := (others => 0);
        Result : Integer;
    begin
        for I in 1 .. Coin_Count loop
            if Coins(I) <= 0 then
                Put_Line("Error: Coin value" & Integer'Image(Coins(I)) & " is invalid (must be positive).");
                return -1;
            end if;
        end loop;

        for I in 1 .. Coin_Count loop
            for J in Coins(I) .. Amount loop
                if DP(J - Coins(I)) /= Integer'Last and then DP(J) > DP(J - Coins(I)) + 1 then
                    DP(J) := DP(J - Coins(I)) + 1;
                    Track(J) := I;
                end if;
            end loop;
        end loop;

        if DP(Amount) = Integer'Last then
            return -1;
        end if;

        declare
            Current : Integer := Amount;
        begin
            while Current > 0 loop
                Count(Track(Current)) := Count(Track(Current)) + 1;
                Current := Current - Coins(Track(Current));
            end loop;
        end;

        Put_Line(Integer'Image(Amount) & " ==> " & Integer'Image(DP(Amount)));
        Print_Solution(Coins, Count, Coin_Count);

        Result := DP(Amount);
        return Result;
    end Coin_Change;

    function Read_Coins_From_File(File_Path : String; Coins : out Coin_Array) return Natural is
        File : File_Type;
        Coin_Count : Natural;
    begin
        Open(File, In_File, File_Path);
        Get(File, Coin_Count);

        if Coin_Count <= 0 or Coin_Count > Max_Coins then
            Put_Line("Error: Invalid number of coins in file.");
            Close(File);
            return 0;
        end if;

        for I in 1 .. Coin_Count loop
            Get(File, Coins(I));
            if Coins(I) <= 0 then
                Put_Line("Error: Coin value" & Integer'Image(Coins(I)) & " is invalid (must be positive).");
                Close(File);
                return 0;
            end if;
        end loop;

        Close(File);
        return Coin_Count;
    exception
        when others =>
            Put_Line("Error: Failed to read coins from file.");
            Close(File);
            return 0;
    end Read_Coins_From_File;

    procedure Main is
        Coins : Coin_Array;
        Coin_Count : Natural;
    begin
        if Argument_Count < 2 then
            Put_Line("Usage: " & Command_Name & " <coin_file> <amount1> <amount2> ...");
            return;
        end if;

        Coin_Count := Read_Coins_From_File(Argument(1), Coins);
        if Coin_Count = 0 then
            return;
        end if;

        for I in 2 .. Argument_Count loop
            declare
                Amount : Integer;
            begin
                Amount := Integer'Value(Argument(I));
                if Amount < 0 then
                    Put_Line("Error: Invalid amount '" & Argument(I) & "'. Amounts must be non-negative integers.");
                    return;
                end if;
                if Coin_Change(Coins, Coin_Count, Amount) = -1 then
                    Put_Line(Integer'Image(Amount) & " ==> No solution!");
                end if;
            end;
        end loop;
    end Main;

begin
    Main;
end coinchange;
