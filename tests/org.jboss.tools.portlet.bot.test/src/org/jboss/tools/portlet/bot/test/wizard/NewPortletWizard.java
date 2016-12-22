/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.tools.portlet.bot.test.wizard;

import org.jboss.reddeer.jface.wizard.NewWizardDialog;

public class NewPortletWizard extends NewWizardDialog {

	public NewPortletWizard() {
		super("JBoss Tools Web", "Portlet", "Java Portlet");
	}
	
}
