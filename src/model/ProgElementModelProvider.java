package model;

import java.util.ArrayList;
import java.util.List;

public enum ProgElementModelProvider {
   INSTANCE;

   private List<ProgramElement> pElements = new ArrayList<ProgramElement>();

   public List<ProgramElement> getProgElements() {
      return pElements;
   }

   public ProgramElement addProgramElement(ProgramElement pElem) {
      for (ProgramElement iElem : pElements) {
         if (iElem.getName().equals(pElem.getName())) {
            return iElem;
         }
      }
      this.pElements.add(pElem);
      return null;
   }

   public void clearProgramElements() {
      this.pElements.clear();
   }
}
