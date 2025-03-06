with Ada.Unchecked_Deallocation;

package list is
   type ListT is private;

   function isEmpty (l : ListT) return Boolean;
   function Pop (l : in out ListT) return Integer;
   procedure Push (l : in out ListT; e : Integer);
   procedure Append (l : in out ListT; e : Integer);
   --dodane:
   function Get (l : ListT; i : Integer ) return Integer;
   procedure Put (l : in out ListT; i : Integer; e : Integer);
   procedure Insert (l : in out ListT; i : Integer; e : Integer);
   procedure Usun (l : in out ListT; i : Integer); --usun
   procedure Clean (l : in out ListT);
   --koniec
   procedure Print (l : ListT);
   function Length (l : ListT) return Integer;
private
   type Node;
   type NodePtr is access Node;
   type Node is record
      elem : Integer := 0;
      next : NodePtr := null;
   end record;

   type ListT is record
      first : NodePtr := null;
      last  : NodePtr := null;
      length : Integer := 0;
   end record;

   procedure Free is
      new Standard.Ada.Unchecked_Deallocation (Node, NodePtr);
end list;