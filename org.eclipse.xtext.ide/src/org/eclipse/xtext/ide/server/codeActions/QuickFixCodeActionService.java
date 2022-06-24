/*******************************************************************************
 * Copyright (c) 2020 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.ide.server.codeActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionKind;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.xtext.ide.editor.quickfix.AbstractDeclarativeIdeQuickfixProvider;
import org.eclipse.xtext.ide.editor.quickfix.DiagnosticResolution;
import org.eclipse.xtext.ide.editor.quickfix.IQuickFixProvider;
import org.eclipse.xtext.ide.editor.quickfix.QuickFix;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.xbase.lib.Exceptions;

import com.google.common.annotations.Beta;

/**
 * {@link ICodeActionService2} handling quick-fixes annotated with {@link CodeActionKind#QuickFix}.
 *
 * @author Heinrich Weichert
 *
 * @see AbstractDeclarativeIdeQuickfixProvider
 * @see QuickFix
 *
 * @since 2.24
 */
@Beta
public class QuickFixCodeActionService implements ICodeActionService2 {

	@Inject
	private IQuickFixProvider quickfixes;

	@Override
	public List<Either<Command, CodeAction>> getCodeActions(Options options) {
		boolean handleQuickfixes = options.getCodeActionParams().getContext().getOnly() == null
				|| options.getCodeActionParams().getContext().getOnly().isEmpty()
				|| options.getCodeActionParams().getContext().getOnly().contains(CodeActionKind.QuickFix);

		if (!handleQuickfixes) {
			return Collections.emptyList();
		}

		List<Either<Command, CodeAction>> result = new ArrayList<>();
		for (Diagnostic diagnostic : options.getCodeActionParams().getContext().getDiagnostics()) {
			if (quickfixes.handlesDiagnostic(diagnostic)) {
				try {
					result.addAll(options.getLanguageServerAccess()
							.doRead(options.getURI(), (ILanguageServerAccess.Context context) -> {
								options.setDocument(context.getDocument());
								options.setResource(context.getResource());

								List<Either<Command, CodeAction>> codeActions = new ArrayList<>();
								quickfixes.getResolutions(options, diagnostic).stream()
										.sorted(Comparator
												.nullsLast(Comparator.comparing(DiagnosticResolution::getLabel)))
										.forEach(r -> codeActions.add(Either.forRight(createFix(r, diagnostic))));
								return codeActions;
							}).get());
				} catch (InterruptedException | ExecutionException e) {
					throw Exceptions.sneakyThrow(e);
				}
			}
		}
		return result;
	}

	private CodeAction createFix(DiagnosticResolution resolution, Diagnostic diagnostic) {
		CodeAction codeAction = new CodeAction();
		codeAction.setDiagnostics(Collections.singletonList(diagnostic));
		codeAction.setTitle(resolution.getLabel());
		codeAction.setEdit(resolution.apply());
		codeAction.setKind(CodeActionKind.QuickFix);

		return codeAction;
	}

}
