with Ada.Text_IO; use Ada.Text_IO;
with Ada.Float_Text_IO; use Ada.Float_Text_IO;
with Ada.Numerics.Elementary_Functions; use Ada.Numerics.Elementary_Functions;

procedure findzero is

   type FuncType is access function (X : Float) return Float;

   function Test_Function (X : Float) return Float is
   begin
      return Cos(X / 2.0);
   end Test_Function;

   function FindZero (F : FuncType; A, B : in out Float; Eps : Float) return Float is
      C, Prev_C : Float := A;
   begin
      if F(A) * F(B) >= 0.0 then
         Put_Line("Error: the function must have different signs at the ends of the interval.");
         return 0.0;
      end if;

      while ((B-A)/2.0 > Eps) loop
         C := (A + B) / 2.0;

         if F(C) = 0.0 then
            return C;
         elsif F(A) * F(C) < 0.0 then
            B := C;
         else
            A := C;
         end if;

         if Abs(C - Prev_C) < Eps then
            exit;
         end if;

         Prev_C := C;
      end loop;

      return C;
   end FindZero;

   A, B, Zero : Float;
   Eps : Float;

begin
   A := 2.0;
   B := 4.0;
   for I in 1 .. 8 loop
      Eps := 10.0 ** (-Float(I));
      Zero := FindZero(Test_Function'Access, A, B, Eps);
      Put("Zero point: ");
      Put(Zero, Fore => 0, Aft => I, Exp => 0);
      Put(" (found with precision ε = ");
      Put(Eps, Fore => 0, Aft => I, Exp => 0);
      Put_Line(")");
   end loop;
end findzero;

