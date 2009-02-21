/*******************************************************************************
 * Copyright (c) 2009 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.parser.packrat.tokens;

import org.eclipse.xtext.Group;
import org.eclipse.xtext.parser.packrat.IParsedTokenVisitor;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public class GroupToken extends CompoundParsedToken {

	public GroupToken(int offset, Group group) {
		super(offset, group);
	}

	@Override
	public void accept(IParsedTokenVisitor visitor) {
		visitor.visitGroupToken(this);
	}

	@Override
	public Group getGrammarElement() {
		return (Group) super.getGrammarElement();
	}

	public static class End extends CompoundParsedToken.End {

		public End(int offset) {
			super(offset);
		}

		@Override
		public void accept(IParsedTokenVisitor visitor) {
			visitor.visitGroupTokenEnd(this);
		}

	}

}
