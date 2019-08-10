package model.editing;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerCell;

public class ProgElemEditingSupport extends EditingSupport {
   protected TreeViewer viewer;
   protected TextCellEditor editor;

   public ProgElemEditingSupport(TreeViewer viewer) {
      super(viewer);
      this.viewer = viewer;
      this.editor = new TextCellEditor(viewer.getTree());
   }

   @Override
   protected CellEditor getCellEditor(Object element) {
      return this.editor;
   }

   @Override
   protected boolean canEdit(Object element) {
      return true;
   }

   @Override
   protected void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell) {
      super.saveCellEditorValue(cellEditor, cell);
   }

   @Override
   protected Object getValue(Object element) {
      return null;
   }

   @Override
   protected void setValue(Object element, Object value) {
   }
}
