package model.editing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.TreeItem;

import analysis.ProjectAnalyzer;
import analysis.ReplaceMethodParamAnalyzer;
import model.MethodElement;
import model.ProgElementModelProvider;
import model.ProgramElement;
import view.Viewer;

public class MethodParameterEditingSupport extends ProgElemEditingSupport {
   private Viewer myViewer;

   public MethodParameterEditingSupport(Viewer myViewer) {
      super(myViewer.getViewer());
      this.myViewer = myViewer;
   }

   @Override
   protected Object getValue(Object element) {
      if (element instanceof MethodElement) {
         MethodElement m = (MethodElement) element;
         return m.getParameterStr();
      }
      return element;
   }

   @Override
   protected void setValue(Object element, Object value) {
      MethodElement p = (MethodElement) element;
      String params = String.valueOf(value).trim();
      List<String> parameters = null;
      if (p.getParameterStr().equalsIgnoreCase(params)) {
         return;
      }
      if (params.equalsIgnoreCase("void")) {
         parameters = new ArrayList<String>();
      } else {
         parameters = Arrays.asList(params.split(","));
      }
      new ReplaceMethodParamAnalyzer(p, parameters);
      p.setParameters(parameters);
      this.viewer.update(element, null);
      refreshViewer();
   }

   @Override
   protected void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
      super.saveCellEditorValue(cellEditor, cell);
   }

   void refreshViewer() {
      viewer.getTree().deselectAll(); // resolved issue: Stack overflow errors.
      ProjectAnalyzer analyzer = new ProjectAnalyzer();
      ProgElementModelProvider.INSTANCE.clearProgramElements();
      analyzer.analyze();
      List<ProgramElement> data = ProgElementModelProvider.INSTANCE.getProgElements();
      ProgramElement[] array = data.toArray(new ProgramElement[data.size()]);
      viewer.setInput(array);

      TreeItem[] items = viewer.getTree().getItems();
      for (TreeItem treeItem : items) {
         expandTreeItem(treeItem);
      }
   }

   void expandTreeItem(TreeItem it) {
      ProgramElement p = (ProgramElement) it.getData();
      if (p == null) {
         return;
      }
      if (myViewer.selectedPath().contains(p.getName())) {
         this.viewer.setExpandedState(p, true);
         this.viewer.refresh(p, true);
      }
      for (TreeItem child : it.getItems()) {
         expandTreeItem(child);
      }
   }
}
