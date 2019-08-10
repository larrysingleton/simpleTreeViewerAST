
package view;

import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import analysis.ProjectAnalyzer;
import model.ProgElementModelProvider;
import model.ProgramElement;
import view.progelement.ContentProviderProgElem;
import view.progelement.LabelProviderMethodParm;
import view.progelement.LabelProviderProgElem;

public class Viewer {
   public static final String ID = "simpletreeviewerastexample.partdescriptor.simpleasttreeview";
   private TreeViewer viewer;

   @PostConstruct
   public void postConstruct(Composite parent) {
      viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
      createProgElemColumns();
      createContextMenu(parent);
   }

   /**
    * Question - Add another column to show the size of a method (e.g., the number of lines or the number of characters).
    */
   private void createProgElemColumns() {
      viewer.setContentProvider(new ContentProviderProgElem());
      viewer.getTree().setHeaderVisible(true);
      String[] titles = { "Program Element", "Method Parameter" };
      int[] bounds = { 300, 100 };
      // First column
      TreeViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
      col.setLabelProvider(new DelegatingStyledCellLabelProvider(new LabelProviderProgElem()));
      // Second column
      col = createTableViewerColumn(titles[1], bounds[1], 1);
      col.setLabelProvider(new DelegatingStyledCellLabelProvider(new LabelProviderMethodParm()));
   }

   private TreeViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
      final TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
      final TreeColumn column = viewerColumn.getColumn();
      column.setText(title);
      column.setWidth(bound);
      column.setResizable(true);
      column.setMoveable(true);
      return viewerColumn;
   }

   private void createContextMenu(Composite parent) {
      Menu contextMenu = new Menu(viewer.getTree());
      viewer.getTree().setMenu(contextMenu);
      createMenuItem(contextMenu);
   }

   private void createMenuItem(Menu parent) {
      final MenuItem menuItem1 = new MenuItem(parent, SWT.PUSH);
      menuItem1.setText("Update");
      menuItem1.addSelectionListener(new SelectionAdapter() {
         public void widgetSelected(SelectionEvent e) {
            updateView();
         }
      });

      final MenuItem menuItem2 = new MenuItem(parent, SWT.PUSH);
      menuItem2.setText("Expand");
      menuItem2.addSelectionListener(new SelectionAdapter() {
         public void widgetSelected(SelectionEvent e) {
            TreeItem[] selection = viewer.getTree().getSelection();
            if (selection != null && selection.length > 0) {
               TreeColumn[] treeColumns = selection[0].getParent().getColumns();
               for (TreeColumn treeColumn : treeColumns) {
                  treeColumn.pack();
               }
               expandTree(selection[0]);
            }
         }
      });
   }

   public void updateView() {
      viewer.getTree().deselectAll(); // resolved issue: stack overflow errors.
      ProgElementModelProvider.INSTANCE.clearProgramElements();
      try {
         ProjectAnalyzer analyzer = new ProjectAnalyzer();
         analyzer.analyze();
      } catch (CoreException e) {
         e.printStackTrace();
      }
      List<ProgramElement> data = ProgElementModelProvider.INSTANCE.getProgElements();
      ProgramElement[] array = data.toArray(new ProgramElement[data.size()]);
      viewer.setInput(array);
   }

   void expandTree(TreeItem it) {
      ProgramElement p = (ProgramElement) it.getData();
      if (p == null) {
         return;
      }
      this.viewer.setExpandedState(p, true);
      for (TreeItem child : it.getItems()) {
         expandTree(child);
      }
   }

   @Focus
   public void setFocus() {
      viewer.getControl().setFocus();
   }
}