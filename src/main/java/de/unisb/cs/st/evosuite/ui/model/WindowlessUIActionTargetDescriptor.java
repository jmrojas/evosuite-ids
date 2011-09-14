package de.unisb.cs.st.evosuite.ui.model;

import java.awt.Component;
import java.io.Serializable;
import java.util.*;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang.StringUtils;
import org.uispec4j.Panel;
import org.uispec4j.UIComponent;
import org.uispec4j.Window;

import de.unisb.cs.st.evosuite.ui.run.FocusOrder;
import de.unisb.cs.st.evosuite.utils.ArrayUtil;
import de.unisb.cs.st.evosuite.utils.HashUtil;

class WindowlessUIActionTargetDescriptor implements Serializable {
	private static final long serialVersionUID = 1L;

	static class Criteria extends HashMap<String, String> {
		public static Criteria forComponent(UIComponent comp) {
			assert (comp != null);
			
			Component awtComp = comp.getAwtComponent();
			Criteria result = new Criteria();
			String label = comp.getLabel();

			if (comp instanceof Window) {
				Window window = (Window) comp;
				label = window.getTitle();
			}
			
			if (label == null && awtComp instanceof AbstractButton) {
				Action action = ((AbstractButton) awtComp).getAction();
				
				if (action != null) {
					label = action.getValue(Action.NAME).toString();
				}
			}
			
			// Note: the following criteria do not matter in selecting action targets.
			// However, we might want to consider these criteria for state differentiation in the future.
			// (Actions whose outcome depends on application state we can observe in the ui... see comment in WindowDescriptor)
			
//			if (comp instanceof TextBox) {
//				TextBox textBox = (TextBox) comp;
//				label = textBox.getText();
//			}
//
//			if (comp instanceof Tree || comp instanceof ListBox || comp instanceof Table) {
//				try {
//					Method getContent = comp.getClass().getDeclaredMethod("getContent");
//					getContent.setAccessible(true);
//					label = getContent.invoke(comp);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}

			result.put("class", awtComp.getClass().getName());
			result.put("label", label);
			result.put("name", !(comp instanceof Window) ? comp.getName() : null);
			
			return result;
		}

		private static final long serialVersionUID = 1L;

		private Criteria() {
			super();
		}

		public boolean match(UIComponent component) {
			return Criteria.forComponent(component).equals(this);
		}
		
		public boolean match(UIActionTargetDescriptor desc) {
			return desc.getCriteria().equals(this);
		}
		
		@Override
		public String toString() {
			Set<String> knownKeys = ArrayUtil.asSet("class", "label", "name");
				
			if (this.keySet().equals(knownKeys)) {
				String label = this.get("label");
				String name = this.get("name");
				
				List<String> parts = new LinkedList<String>();

				if (label != null) {
					parts.add(String.format("\"%s\"", label));
				}
				
				if (name != null) {
					parts.add(String.format("name=%s", name));
				}
				
				String criteria = parts.isEmpty() ? "" : String.format("[%s]", StringUtils.join(parts, ", "));

				return String.format("%s%s", this.get("class"), criteria);
			} else {
				return super.toString();
			}
		}
	}
	
	/**
	 * Swing TextComponents are 'enabled' (according to the terms of the UIComponent method) even when they can not be edited.
	 * This method only returns true if a component can be edited.
	 * 
	 * @param comp
	 * @return
	 */
	public static boolean isComponentEnabled(UIComponent comp) {
		assert(comp != null);
		
		Component awtComp = comp.getAwtComponent();
		boolean enabled = awtComp.isEnabled();
		
		if (enabled && awtComp instanceof JTextComponent) {
			JTextComponent textComp = (JTextComponent) awtComp;
			boolean editable = textComp.isEditable();
			enabled &= editable;
		}
		
		return enabled;
	}

	public static List<WindowlessUIActionTargetDescriptor> allFor(Window window) {
		List<WindowlessUIActionTargetDescriptor> result = new LinkedList<WindowlessUIActionTargetDescriptor>();
		
		Iterable<UIComponent> children = FocusOrder.children(window);
		
		for (UIComponent comp : children) {
			if (comp != null && isComponentEnabled(comp)) {
				result.add(new WindowlessUIActionTargetDescriptor(children, comp));
			}
		}
		
		return result;
	}
	
	private static int matchIdxFor(Iterable<UIComponent> children, UIComponent comp, Criteria criteria) {
		Component targetComp = comp.getAwtComponent();

		int matchIdx = -1;
		
		for (UIComponent curComp : children) {
			if (curComp != null && criteria.match(curComp)) {
				matchIdx++;
				
				if (curComp.getAwtComponent() == targetComp) {
					return matchIdx;
				}
			}
		}
		
		assert (false);
		return -1;
	}


	private Criteria criteria;
	private int matchIdx;
	private Class<? extends UIComponent> type;

	public WindowlessUIActionTargetDescriptor(Iterable<UIComponent> children, UIComponent comp) {
		assert (children != null);
		assert (comp != null);
		
		this.criteria = Criteria.forComponent(comp);
		this.matchIdx = matchIdxFor(children, comp, criteria);
		this.type = comp.getClass();
	}

	public UIComponent resolve(Panel container) {
		int curMatchIdx = -1;
		
		for (UIComponent curComp : FocusOrder.children(container)) {
			if (curComp != null && this.criteria.match(curComp)) {
				curMatchIdx++;
				
				if (curMatchIdx == this.matchIdx) {
					assert (type.isAssignableFrom(curComp.getClass()));
					return curComp;
				}
			}
		}
		
		return null;
	}
	
	public boolean canResolve(WindowDescriptor windowDescriptor) {
		for (UIActionTargetDescriptor desc : windowDescriptor.getActionTargetDescriptors()) {
			if (this.canResolve(desc.getTargetDescriptor())) {
				return true;
			}
		}
		
		return false;
	}

	public boolean canResolve(WindowlessUIActionTargetDescriptor targetDescriptor) {
		return this.equals(targetDescriptor);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof WindowlessUIActionTargetDescriptor)) {
			return false;
		}
		
		WindowlessUIActionTargetDescriptor other = (WindowlessUIActionTargetDescriptor) obj;
		return this.matchIdx == other.matchIdx && this.criteria.equals(other.criteria);
	}

	@Override
	public int hashCode() {
		return HashUtil.hashCode(this.matchIdx, this.criteria.hashCode());
	}

	String innerString() {
		return String.format("match #%d of %s", this.matchIdx, this.criteria);
	}
	
	@Override
	public String toString() {
		return String.format("WindowlessUIActionTargetDescriptor[%s]", this.innerString());
	}

	public Class<? extends UIComponent> getType() {
		return this.type;
	}

	public Map<String,String> getCriteria() {
		return this.criteria;
	}
}
