with Ada.Command_Line; use Ada.Command_Line;
with Ada.Text_IO; use Ada.Text_IO;
with Totient;

procedure Program is
begin
    if Argument_Count = 0 then
        Put_Line("Usage: ./program <number1> <number2> ...");
        return;
    end if;

    for I in 1 .. Argument_Count loop
        declare
            Arg  : String := Argument(I);
            N    : Positive;
        begin
            N := Positive'Value(Arg);

            Put("totient(" & Integer'Image(N) & ") = ");
            Put_Line(Integer'Image(Totient.Totient(N)));
        exception
            when Constraint_Error =>
                Put_Line("Error: Invalid input '" & Arg & "'. Input must be a positive integer.");
            when others =>
                Put_Line("Error: An unexpected error occurred with input '" & Arg & "'.");
        end;
    end loop;
end Program;

