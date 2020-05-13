package com.sma.ui.core.jobdetails.commvault;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.sma.ui.core.jobdetails.AbstractSubJobsDetailsWidget;
import com.sma.ui.core.jobdetails.windows.AbstractWindowsSubJobDetailsWidgetExtension;

public class CommVaultSubJobDetailExtension extends AbstractWindowsSubJobDetailsWidgetExtension {

	@Override
	public AbstractSubJobsDetailsWidget createJobDetailsWidget(Composite parent) {
		AbstractSubJobsDetailsWidget widget = new CommVaultSubJobDetailsWidget(parent, getMessageDisplayer(), getContextID());
		widget.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		return widget;
	}


}
