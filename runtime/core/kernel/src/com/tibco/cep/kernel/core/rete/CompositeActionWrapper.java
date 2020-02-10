package com.tibco.cep.kernel.core.rete;

import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;

public class CompositeActionWrapper implements CompositeAction
{
	private Action action;
	
	public CompositeActionWrapper(Action action) {
		this.action = action;
	}

	@Override
	public Identifier[] getIdentifiers() {
		return action.getIdentifiers();
	}

	@Override
	public void execute(Object[] objects) {
		action.execute(objects);
		
	}

	@Override
	public Rule getRule() {
		return action.getRule();
	}

	@Override
	public int getComponentCount() {
		return 1;
	}

	@Override
	public void executeComponent(int index, Object[] objects) {
		if(index != 0) throw new IndexOutOfBoundsException(String.valueOf(index));
		execute(objects);
	}

}
