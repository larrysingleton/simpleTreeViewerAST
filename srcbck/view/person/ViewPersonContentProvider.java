package view.person;

import org.eclipse.jface.viewers.ITreeContentProvider;

import model.person.Person;
import view.Viewer;

public class ViewPersonContentProvider implements ITreeContentProvider {
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return (Person[]) inputElement;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Person p = (Person) parentElement;
		return p.list();
	}

	@Override
	public Object getParent(Object element) {
		Person p = (Person) element;
		return p.getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
		Person p = (Person) element;
		return p.hasChildren();
	}
}
