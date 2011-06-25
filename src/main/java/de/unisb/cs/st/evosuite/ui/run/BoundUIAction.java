package de.unisb.cs.st.evosuite.ui.run;

import org.uispec4j.UIComponent;

import de.unisb.cs.st.evosuite.ui.model.UIAction;
import de.unisb.cs.st.evosuite.utils.HashUtil;

public class BoundUIAction<T extends UIComponent> {
	private UIAction<T> action;
	private T component;

	public BoundUIAction(UIAction<T> action, T component) {
		assert (action != null);
		assert (component != null);

		this.action = action;
		this.component = component;
	}

	public void execute(AbstractUIEnvironment env) {
		this.action.executeOn(env, this.component);
	}

	@Override
	public int hashCode() {
		return HashUtil.hashCode(this.action, this.component);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || !(obj instanceof BoundUIAction<?>)) {
			return false;
		}

		BoundUIAction<T> other = (BoundUIAction<T>) obj;
		return this.action.equals(other.action) && this.component.equals(other.component);
	}

	@Override
	public String toString() {
		return String.format("BoundUIAction[%s on %s]", this.action, this.component);
	}
}
