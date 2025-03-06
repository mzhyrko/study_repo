with Ada.Text_IO; use Ada.Text_IO;
with Ada.Float_Text_IO; use Ada.Float_Text_IO;
with Ada.Numerics.Elementary_Functions; use Ada.Numerics.Elementary_Functions;

procedure ada_quad is
    a: Float;
    b: Float;
    c: Float;
    discr: Float;

begin
    Put_Line("Wprowadz wspolczynniki dla rownania ax*x + bx + c = 0");
    Put("a = ");
    Get(a);
    Put("b = ");
    Get(b);
    Put("c = ");
    Get(c);

    discr := (b * b) - (4.0 * a * c);

    if discr < 0.0 then
        Put_Line("Nie ma rozwiazan");
    elsif discr = 0.0 then
        Put_Line("Rownanie ma 1 rozwiazanie: " & Float'Image((-b) / (2.0 * a)));
    elsif discr > 0.0 then
        Put_Line("Rownanie ma 2 rozwiazania: " &
                  Float'Image((-b + Sqrt(discr)) / (2.0 * a)) &
                  " i " &
                  Float'Image((-b - Sqrt(discr)) / (2.0 * a)));
    end if;

end ada_quad;
