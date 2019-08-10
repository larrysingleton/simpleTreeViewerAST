package model;

import java.util.ArrayList;
import java.util.List;

public class ProgramElement {
   List<ProgramElement> listChildren = new ArrayList<ProgramElement>();
   ProgramElement parent = null;
   String name = null;

   public ProgramElement(String name) {
      this.name = name;
      parent = this;
   }

   public ProgramElement(String name, ProgramElement parent) {
      this.name = name;
      this.parent = parent;
   }

   public ProgramElement[] list() {
      ProgramElement[] l = new ProgramElement[listChildren.size()];
      return listChildren.toArray(l);
   }

   public void add(ProgramElement p) {
      listChildren.add(p);
   }

   public ProgramElement getParent() {
      return this.parent;
   }

   public boolean hasChildren() {
      return !this.listChildren.isEmpty();
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return this.name;
   }
}
