package org.exsyst.ui.run;

import org.exsyst.ui.model.states.AbstractUIState;

public interface UIController {
	public void processState(UIRunner uiRunner, AbstractUIState state);

	public void finished(UIRunner uiRunner);
}
