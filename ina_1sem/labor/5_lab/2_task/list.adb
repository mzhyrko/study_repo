with Ada.Text_IO; use Ada.Text_IO;

package body list is
   function isEmpty (l : ListT) return Boolean is
   begin
      return l.first = null;
   end isEmpty;

   function Pop (l : in out ListT) return Integer is
      n : NodePtr := l.first;
      e : Integer := n.elem;
   begin
      l.first := n.next;
      if l.first = null then -- last element
         l.last := null;
      end if;
      Free (n);
      l.length := l.length-1; --len
      return e;
   end Pop;

   procedure Push (l : in out ListT; e : Integer) is
      n : NodePtr := new Node;
   begin
      n.elem := e;
      n.next := l.first;
      l.first := n;
      if l.last = null then -- first element
         l.last := n;
      end if;
      l.length := l.length+1; --len
   end Push;

   procedure Append (l : in out ListT; e : Integer) is
      n : NodePtr := new Node;
   begin
      n.elem := e;
      if l.first = null then -- first element
         l.first := n;
      else
         l.last.next := n;
      end if;
      l.last := n;
      l.length := l.length+1; --len
   end Append;
   --dodane:
   function Get (l : ListT; i : Integer ) return Integer is
      n : NodePtr := l.first;
      indeks : Integer := 1;
   begin
      while indeks/=i loop
         n := n.next;
         indeks := indeks+1;
      end loop;
      return n.elem;
   end Get;

   procedure Put (l : in out ListT; i : Integer; e : Integer) is
      n : NodePtr := l.first;
      current_index : Integer := 1;
   begin
      while(current_index/=i) loop
         n:=n.next;
         current_index := current_index+1;
      end loop;
      n.elem := e;
   end Put;

   procedure Insert (l : in out ListT; i : Integer; e : Integer) is
   begin
      if(i=1) then
         Push(l,e);
      elsif(i=Length(l)+1) then
         Append(l,e);
      else
         declare
            n : NodePtr := new Node;
            temp : NodePtr;
            indeks : NodePtr := l.first;
            current_index : Integer := 1;
         begin
            n.elem := e;
            while(current_index/=i-1) loop
               indeks:=indeks.next;
               current_index:=current_index+1;
            end loop;
            temp:=indeks.next;
            indeks.next:=n;
            n.next:=temp;
            l.length:=l.length+1; --len
         end;
      end if;
   end Insert;

   procedure Usun (l : in out ListT; i : Integer) is --usun
      n : NodePtr := l.first;
      temp : NodePtr;
      current_index : Integer := 1;
   begin
      if(i=1) then
         l.first:=l.first.next;
         Free(n);
      else
         while(current_index/=i-1) loop --n bedzie wskazywal na poprzedzajacy do skasowania
            n:=n.next;
            current_index:=current_index+1;
         end loop;
         temp:=n.next; --temp wskazuje na ten do skasowania
         n.next:=n.next.next;
         Free(temp);
      end if;
      if(l.first=null) then --jezeli usunelismy 1 element a byl on jedyny
         l.last:=null;
      end if;
      l.length:=l.length-1; --len
   end Usun;

   procedure Clean (l : in out ListT) is
      n : NodePtr := l.first;
      kolejny : NodePtr;
   begin
      while(n/=null) loop
         kolejny:=n.next;
         Free(n);
         n := kolejny;
      end loop;
      l.first := null;
      l.last := null;
      l.length := 0; --len
   end Clean;

   --koniec
   procedure Print (l : ListT) is
      n : NodePtr := l.first;
   begin
      while n /= null loop
         Put (n.elem'Image);
         n := n.next;
      end loop;
      Put_Line (" (" & Length (l)'Image & " )");
   end Print;

   function Length (l : ListT) return Integer is
   begin
      return l.length;
   end Length;
end list;
