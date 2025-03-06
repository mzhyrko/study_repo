with Ada.Text_IO; use Ada.Text_IO;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;
with Ada.Strings.Unbounded; use  Ada.Strings.Unbounded;
with Ada.Strings.Unbounded.Text_IO; use Ada.Strings.Unbounded.Text_IO;

with list; use list;

procedure listTest is
   l : ListT;
   r,e : Integer;
   command : Unbounded_String;
   continue : Boolean := True;
begin
   while continue loop
      Put ("Command: ");
      Get_Line (command);
      if command = "Pop" then
         if not isEmpty (l) then
            r := Pop (l);
            Put_Line ("Result:" & r'Image);
         else
            Put_Line ("Error - stack is empty!");
         end if;
      elsif command = "Push" then
         Put ("Value: ");
         Get (r);
         Skip_Line;
         Push (l, r);
         Put_Line ("Result: OK");
      elsif command = "Append" then
         Put ("Value: ");
         Get (r);
         Skip_Line;
         Append (l, r);
         Put_Line ("Result: OK");
      --moje
      elsif command = "Get" then
         Put ("Index: ");
         Get (r);
         Skip_Line;
         if(Length(l)<r or r<1) then
            Put_Line ("Error - bad index!");
         else
            Put_Line("Result:" & Get(l, r)'Image);
         end if;
      elsif command = "Put" then
         Put ("Index: ");
         Get (r);
         Skip_Line;
         if(Length(l)<r or r<1) then
            Put_Line("Error - bad index!");
         else
            Put ("Value: ");
            Get (e);
            Skip_Line;
            Put(l,r,e);
            Put_Line ("Result: OK");
         end if;
      elsif command = "Insert" then
         Put ("Index: ");
         Get (r);
         Skip_Line;
         if(Length(l)+1<r or r<1) then -- len+1 bo mozna insertowac za ostatni element
            Put_Line("Error - bad index!");
         else
            Put ("Value: ");
            Get (e);
            Skip_Line;
            Insert(l,r,e);
            Put_Line ("Result: OK");
         end if;
      elsif command = "Delete" then
         if(isEmpty(l)) then
            Put_Line ("Error - stack is empty!");
         else
            Put ("Index: ");
            Get (r);
            Skip_Line;
            if(Length(l)<r or r<1) then
               Put_Line("Error - bad index!");
            else
               usun(l,r);
               Put_Line ("Result: OK");
            end if;
         end if;
      elsif command = "Clean" then
         Clean(l);
         Put_Line ("Result: OK");
      --koniec
      elsif command = "Print" then
         Put ("Result:");
         Print (l);
      elsif command = "Length" then
         r := Length (l);
         Put_Line ("Result:" & r'Image);
      elsif command = "Exit" then
         continue := False;
      else
         Put_Line ("Unknown command!");
      end if;
   end loop;
   --  clean list
   while not isEmpty (l) loop
      r := Pop (l);
   end loop;
end listTest;