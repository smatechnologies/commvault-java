package com.sma.ui.core.jobdetails.commvault;

import java.text.MessageFormat;
import java.util.List;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.sma.core.OpconException;
import com.sma.core.api.constants.ExitCodeAdvancedConstants;
import com.sma.core.api.constants.SystemConstants;
import com.sma.core.api.interfaces.IPersistentJob;
import com.sma.core.api.interfaces.ISpecificJobProperties;
import com.sma.core.api.job.specific.WindowsJobProperties;
import com.sma.core.api.master.Token;
import com.sma.core.session.ContextID;
import com.sma.core.util.Util;
import com.sma.ui.core.jobdetails.CommandLineTokenizer;
import com.sma.ui.core.jobdetails.JobUtil;
import com.sma.ui.core.jobdetails.windows.AbstractWindowsSubJobDetailsWidget;
import com.sma.ui.core.messages.IMessageDisplayer;
import com.sma.ui.core.widgets.base.CTabFolder2;
import com.sma.ui.core.widgets.job.ExitCodeAdvancedWidget;
import com.sma.ui.core.widgets.listeners.ControlTokenSelectorListener;
import com.sma.ui.core.widgets.listeners.DirtyKeyAdapter;
import com.sma.ui.core.widgets.listeners.DirtySelectionAdapter;
import com.sma.ui.core.widgets.validation.ValidationMessage;

public class CommVaultSubJobDetailsWidget extends AbstractWindowsSubJobDetailsWidget {

	private static final String COMMAND_SUFFIX = SystemConstants.BACK_SLASH + "CVault.exe"; //$NON-NLS-1$

	private CTabFolder2 _folder;
	private CTabItem _jobDefinitionTab;
	private CTabItem _failureCriteriaTab;

	private Text _locPathText;
	private Text _clientText;
	private Text _subClientText;
	private Text _instanceText;
	private Text _backupsetNameText;
	private Label _xmlTemplateLabel;
	private Combo _xmlTemplateCombo;
	private Label _backupTypeLabel;
	private Combo _backupTypeCombo;

	private ExitCodeAdvancedWidget _advancedExitCodeWidget;

	private ContextID contextID = new ContextID("AzureProperty");

	private String[] backupTypes = null;
	private String[] backupFileNames = null;

	public CommVaultSubJobDetailsWidget(Composite parent,	IMessageDisplayer messageManager, ContextID context) {
		super(parent, messageManager, context);

		Composite selector = new Composite(this, SWT.NONE);
		selector.setLayout(new GridLayout(2, false));
		selector.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		
		_folder = new CTabFolder2(selector, SWT.NONE);
		_folder.setLayout(new GridLayout(1, false));
		_folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		_folder.applyFormStyle();

		_jobDefinitionTab = createJobDefinitionTab(_folder);
		_failureCriteriaTab = createFailureCriteriaTab(_folder);
		
		addListeners();
		
		backupTypes = getPropertyValues(CommVaultConstants.BACKUP_TYPES_PROPERTY_NAME);
		if(backupTypes != null) {
			_backupTypeCombo.setItems(backupTypes);
		} else {
			_backupTypeCombo.setItems(CommVaultConstants.BACKUP_TYPES);
		}

		backupFileNames = getPropertyValues(CommVaultConstants.BACKUP_FILE_NAMES_PROPERTY_NAME);
		if(backupFileNames != null) {
			_xmlTemplateCombo.setItems(backupFileNames);
		} else {
			_xmlTemplateCombo.setItems(CommVaultConstants.BACKUP_TYPES);
		}

	}
	
	private CTabItem createJobDefinitionTab(CTabFolder tabFolder) {

		Composite cmpJobDefinitionTab = new Composite(tabFolder, SWT.INHERIT_DEFAULT);
		cmpJobDefinitionTab.setLayout(new GridLayout(1, false));
	
		Composite cmpJobDefinition = new Composite(cmpJobDefinitionTab, SWT.NONE);
		cmpJobDefinition.setLayout(new GridLayout(2, false));
		cmpJobDefinition.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		_locPathText = JobUtil.createLabeledText(cmpJobDefinition,CommVaultConstants.LOCATION_PATH_NAME,0,JobUtil.COLOR_BLUE,JobUtil.COLOR_LIGHT_GREEN, SWT.SINGLE | SWT.BORDER, 1);
		_locPathText.setToolTipText(CommVaultConstants.LOCATION_PATH_TOKEN_NAME_TOOLTIP);

		_clientText = JobUtil.createLabeledText(cmpJobDefinition,CommVaultConstants.CLIENT_NAME_NAME,0,JobUtil.COLOR_BLUE,JobUtil.COLOR_LIGHT_GREEN, SWT.SINGLE | SWT.BORDER, 1);
		_clientText.setToolTipText(CommVaultConstants.CLIENT_NAME_TOOLTIP);

		_subClientText = JobUtil.createLabeledText(cmpJobDefinition,CommVaultConstants.SUB_CLIENT_NAME_NAME,0,JobUtil.COLOR_BLUE,JobUtil.COLOR_LIGHT_GREEN, SWT.SINGLE | SWT.BORDER, 1);
		_subClientText.setToolTipText(CommVaultConstants.SUB_CLIENT_NAME_TOOLTIP);

		_instanceText = JobUtil.createLabeledText(cmpJobDefinition,CommVaultConstants.INSTANCE_NAME,0,JobUtil.COLOR_BLUE,JobUtil.COLOR_LIGHT_GREEN, SWT.SINGLE | SWT.BORDER, 1);
		_instanceText.setToolTipText(CommVaultConstants.INSTANCE_TOOLTIP);

		_backupsetNameText = JobUtil.createLabeledText(cmpJobDefinition,CommVaultConstants.BACKUPSET_NAME_NAME,0,JobUtil.COLOR_BLUE,JobUtil.COLOR_LIGHT_GREEN, SWT.SINGLE | SWT.BORDER, 1);
		_backupsetNameText.setToolTipText(CommVaultConstants.BACKUPSET_NAME_TOOLTIP);

		_xmlTemplateLabel = new Label(cmpJobDefinition, SWT.TRAIL);
		_xmlTemplateLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
		_xmlTemplateLabel.setForeground(JobUtil.COLOR_BLUE);
		_xmlTemplateLabel.setText(CommVaultConstants.XML_TEMPLATE_NAME);
		
		_xmlTemplateCombo = new Combo(cmpJobDefinition, SWT.READ_ONLY | SWT.DROP_DOWN);
		_xmlTemplateCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		_xmlTemplateCombo.setToolTipText(CommVaultConstants.XML_TEMPLATE_TOOLTIP);

		_backupTypeLabel = new Label(cmpJobDefinition, SWT.TRAIL);
		_backupTypeLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
		_backupTypeLabel.setForeground(JobUtil.COLOR_BLUE);
		_backupTypeLabel.setText(CommVaultConstants.BACKUP_TYPE_NAME);
		
		_backupTypeCombo = new Combo(cmpJobDefinition, SWT.READ_ONLY | SWT.DROP_DOWN);
		_backupTypeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		_backupTypeCombo.setToolTipText(CommVaultConstants.BACKUP_TYPE_TOOLTIP);

		_jobDefinitionTab = JobUtil.createTabItem(tabFolder, cmpJobDefinitionTab, CommVaultConstants.JOB_DEFINITION_TAB_NAME); 	
		return _jobDefinitionTab;
	}

	private CTabItem createFailureCriteriaTab(CTabFolder tabFolder) {

		Composite failureCriteriaTab = new Composite(tabFolder, SWT.NONE);
		failureCriteriaTab.setLayout(new GridLayout(2, false));

		Group failureCriteriaAdvanced = new Group(failureCriteriaTab, SWT.NONE);
		failureCriteriaAdvanced.setLayout(new GridLayout());
		failureCriteriaAdvanced.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		
		_advancedExitCodeWidget = new ExitCodeAdvancedWidget(failureCriteriaAdvanced, this.getMessageDisplayer(), ExitCodeAdvancedConstants.MINIMUM_ROWS_TO_DISPLAY,
				ExitCodeAdvancedConstants.MAXIMUM_ROWS_TO_DISPLAY);
		_advancedExitCodeWidget.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		_failureCriteriaTab = JobUtil.createTabItem(tabFolder,	failureCriteriaTab, CommVaultConstants.FAILURE_CRITERIA_TAB_NAME);
		return _failureCriteriaTab;
	}	
		
	private void addListeners() {
		_locPathText.addKeyListener(new DirtyKeyAdapter(this));
		_clientText.addKeyListener(new DirtyKeyAdapter(this));
		_subClientText.addSelectionListener(new DirtySelectionAdapter(this));
		_instanceText.addKeyListener(new DirtyKeyAdapter(this));
		_backupsetNameText.addKeyListener(new DirtyKeyAdapter(this));
		_xmlTemplateCombo.addSelectionListener(new DirtySelectionAdapter(this));
		_backupTypeCombo.addSelectionListener(new DirtySelectionAdapter(this));

		_advancedExitCodeWidget.addDirtyListener(this);

		// add CTRL+T shortcut for global properties
		new ControlTokenSelectorListener(_locPathText, getContextID());
		new ControlTokenSelectorListener(_clientText, getContextID());
		new ControlTokenSelectorListener(_subClientText, getContextID());
		new ControlTokenSelectorListener(_instanceText, getContextID());
		new ControlTokenSelectorListener(_backupsetNameText, getContextID());

	}

	public void setDefaults() {

		setSendDirtyEvents(false);
		
		_locPathText.setText(CommVaultConstants.LOCATION_PATH_TOKEN);
		_clientText.setText(SystemConstants.EMPTY_STRING);
		_subClientText.setText(SystemConstants.EMPTY_STRING);
		_instanceText.setText(SystemConstants.EMPTY_STRING);
		_backupsetNameText.setText(SystemConstants.EMPTY_STRING);
		_xmlTemplateCombo.select(0);
		_backupTypeCombo.select(0);

		_advancedExitCodeWidget.setDefaults();
		
		_folder.setSelection(_jobDefinitionTab);

		setSendDirtyEvents(true);
		
	}
	
	
	@Override
	protected String getCommandLine() {
		StringBuilder builder = new StringBuilder();
		builder.append(JobUtil.autoQuote(_locPathText.getText().trim() + COMMAND_SUFFIX, true));
		builder.append(SystemConstants.VERTICAL_TAB);
		builder.append(SystemConstants.SIGN_MINUS);
		builder.append(CommVaultConstants.CLIENT_NAME_ARGUMENT);
		builder.append(SystemConstants.VERTICAL_TAB);
		builder.append(SystemConstants.QUOTE);
		builder.append(_clientText.getText().trim());
		builder.append(SystemConstants.QUOTE);
		builder.append(SystemConstants.VERTICAL_TAB);
		builder.append(SystemConstants.SIGN_MINUS);
		builder.append(CommVaultConstants.BACKUPSET_NAME_ARGUMENT);
		builder.append(SystemConstants.VERTICAL_TAB);
		builder.append(SystemConstants.QUOTE);
		builder.append(_backupsetNameText.getText().trim());
		builder.append(SystemConstants.QUOTE);
		builder.append(SystemConstants.VERTICAL_TAB);
		builder.append(SystemConstants.SIGN_MINUS);
		builder.append(CommVaultConstants.XML_TEMPLATE_ARGUMENT);
		builder.append(SystemConstants.VERTICAL_TAB);
		builder.append(SystemConstants.QUOTE);
		builder.append(_xmlTemplateCombo.getText().trim());
		builder.append(SystemConstants.QUOTE);
		builder.append(SystemConstants.VERTICAL_TAB);
		builder.append(SystemConstants.VERTICAL_TAB);
		builder.append(SystemConstants.SIGN_MINUS);
		builder.append(CommVaultConstants.BACKUP_TYPE_ARGUMENT);
		builder.append(SystemConstants.VERTICAL_TAB);
		builder.append(SystemConstants.QUOTE);
		builder.append(_backupTypeCombo.getText().trim());
		builder.append(SystemConstants.QUOTE);
		builder.append(SystemConstants.VERTICAL_TAB);
		if(_subClientText.getText().length() > 0) {
			builder.append(SystemConstants.SIGN_MINUS);
			builder.append(CommVaultConstants.SUBCLIENT_NAME_ARGUMENT);
			builder.append(SystemConstants.VERTICAL_TAB);
			builder.append(SystemConstants.QUOTE);
			builder.append(_subClientText.getText().trim());
			builder.append(SystemConstants.QUOTE);
			builder.append(SystemConstants.VERTICAL_TAB);
		}
		if(_instanceText.getText().length() > 0) {
			builder.append(SystemConstants.SIGN_MINUS);
			builder.append(CommVaultConstants.INSTANCE_ARGUMENT);
			builder.append(SystemConstants.VERTICAL_TAB);
			builder.append(SystemConstants.QUOTE);
			builder.append(_instanceText.getText().trim());
			builder.append(SystemConstants.QUOTE);
			builder.append(SystemConstants.VERTICAL_TAB);
		}
		
		return builder.toString();
		
	}

	@Override
	protected String getWorkingDirectory() {
		return CommVaultConstants.LOCATION_PATH_TOKEN + SystemConstants.BACK_SLASH;
	}

	@Override
	protected void initializeContents(ISpecificJobProperties jobProperties)
			throws OpconException {
		WindowsJobProperties windowsDetails = (WindowsJobProperties) jobProperties;
		try {
			if (windowsDetails != null) {
				final String commandLine = windowsDetails.getCommandLine();
				// basic check first
				if (!commandLine.contains(COMMAND_SUFFIX)) {
					throw new ParseException(
							MessageFormat.format(CommVaultConstants.PARSE_JOB_COMMAND_ERROR,	"Commvault"));
				}
				parseCommandLine(commandLine);
				_advancedExitCodeWidget.initializeContents();
				if (getInput() != null && getInput().getSpecificJobProperties() != null) {
					try {
						WindowsJobProperties _jobProperties = (WindowsJobProperties) getInput().getSpecificJobProperties();
						_advancedExitCodeWidget.setInput(_jobProperties.getExitCodeAdvancedRows());
					} catch (OpconException e) {
						setErrorMessage("Error initializing user selector " + Util.getCauseError(e));
					}
				}
			} else {
				setDefaults();
			}
		} catch (Exception e) {
			throw new OpconException(e);
		}
		
	}
	
	private void parseCommandLine(String commandLine) throws ParseException {

		// extract the location property from the commandline and display this
		int endProperty = commandLine.indexOf(SystemConstants.BACK_SLASH);
		if (endProperty > -1) {
			_locPathText.setText(commandLine.substring(1, endProperty));
		}
		// remove command from commandLine
		commandLine = commandLine.replace(SystemConstants.QUOTE
						+ CommVaultConstants.LOCATION_PATH_TOKEN
						+ COMMAND_SUFFIX + SystemConstants.QUOTE,
						SystemConstants.EMPTY_STRING).trim();
		Options options = new Options();
		options.addOption(CommVaultConstants.CLIENT_NAME_ARGUMENT, true, SystemConstants.EMPTY_STRING);
		options.addOption(CommVaultConstants.BACKUPSET_NAME_ARGUMENT, true, SystemConstants.EMPTY_STRING);
		options.addOption(CommVaultConstants.XML_TEMPLATE_ARGUMENT, true, SystemConstants.EMPTY_STRING);
		options.addOption(CommVaultConstants.BACKUP_TYPE_ARGUMENT, true, SystemConstants.EMPTY_STRING);
		options.addOption(CommVaultConstants.SUBCLIENT_NAME_ARGUMENT, true, SystemConstants.EMPTY_STRING);
		options.addOption(CommVaultConstants.INSTANCE_ARGUMENT, true, SystemConstants.EMPTY_STRING);
		
		String[] arguments = CommandLineTokenizer.tokenize(commandLine, true);
		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, arguments);

		if (cmd.hasOption(CommVaultConstants.CLIENT_NAME_ARGUMENT)) {
			_clientText.setText(removeLeadingTrailingDoubleQuotes(cmd.getOptionValue(CommVaultConstants.CLIENT_NAME_ARGUMENT)));
		}
		if (cmd.hasOption(CommVaultConstants.BACKUPSET_NAME_ARGUMENT)) {
			_backupsetNameText.setText(removeLeadingTrailingDoubleQuotes(cmd.getOptionValue(CommVaultConstants.BACKUPSET_NAME_ARGUMENT)));
		}
		if (cmd.hasOption(CommVaultConstants.XML_TEMPLATE_ARGUMENT)) {
			_xmlTemplateCombo.setText(removeLeadingTrailingDoubleQuotes(cmd.getOptionValue(CommVaultConstants.XML_TEMPLATE_ARGUMENT)));
		}
		if (cmd.hasOption(CommVaultConstants.BACKUP_TYPE_ARGUMENT)) {
			_backupTypeCombo.setText(removeLeadingTrailingDoubleQuotes(cmd.getOptionValue(CommVaultConstants.BACKUP_TYPE_ARGUMENT)));
		}
		if (cmd.hasOption(CommVaultConstants.SUBCLIENT_NAME_ARGUMENT)) {
			_subClientText.setText(removeLeadingTrailingDoubleQuotes(cmd.getOptionValue(CommVaultConstants.SUBCLIENT_NAME_ARGUMENT)));
		}
		if (cmd.hasOption(CommVaultConstants.INSTANCE_ARGUMENT)) {
			_instanceText.setText(removeLeadingTrailingDoubleQuotes(cmd.getOptionValue(CommVaultConstants.INSTANCE_ARGUMENT)));
		}
		_folder.setSelection(_jobDefinitionTab);
	}

	public ValidationMessage checkContents() {
		ValidationMessage message = super.checkContents();
		if (message != null) {
			return message;
		}
		if (!(_clientText.getText().trim().length() > 0)) {
			return new ValidationMessage(this, MessageFormat.format(
					CommVaultConstants.TEXTBOX_CANNOT_BE_EMPTY,
					CommVaultConstants.CLIENT_NAME_NAME),
					IMessageProvider.ERROR, _clientText);
		}
		if (!(_backupsetNameText.getText().trim().length() > 0)) {
			return new ValidationMessage(this, MessageFormat.format(
					CommVaultConstants.TEXTBOX_CANNOT_BE_EMPTY,
					CommVaultConstants.BACKUPSET_NAME_NAME),
					IMessageProvider.ERROR, _backupsetNameText);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.sma.ui.core.widgets.interfaces.IJobTypeDetailsWidget#doSave(org.eclipse.core.runtime.IProgressMonitor, com.sma.core.api.interfaces.IPersistentJob)
	 */
	public ValidationMessage doSave(IProgressMonitor monitor, IPersistentJob toSave) throws OpconException {
		ValidationMessage msg = super.doSave(monitor, toSave);
		if (msg != null) {
			return msg;
		}

		final WindowsJobProperties winJob = (WindowsJobProperties) toSave.getSpecificJobProperties();
		monitor.beginTask("Windows Job Advanced Failure Criteria Properties", 5);

		winJob.setExitCodeAdvancedOperators(_advancedExitCodeWidget.getExitCodeAdvancedOperators());
		monitor.worked(1);

		winJob.setExitCodeAdvancedValues(_advancedExitCodeWidget.getExitCodeAdvancedValues());
		monitor.worked(1);

		winJob.setExitCodeAdvancedEndValues(_advancedExitCodeWidget.getExitCodeAdvancedEndValues());
		monitor.worked(1);

		winJob.setExitCodeAdvancedResults(_advancedExitCodeWidget.getExitCodeAdvancedResults());
		monitor.worked(1);

		winJob.setExitCodeAdvancedComparators(_advancedExitCodeWidget.getExitCodeAdvancedComparators());
		monitor.worked(1);

		monitor.done();

		return null;
	}

	/**
	 * @param input
	 * @return
	 */
	private String removeLeadingTrailingDoubleQuotes(String input) {
		String removed = input.trim();
		if(removed.substring(0,1).equals(SystemConstants.QUOTE)) {
			removed = removed.substring(1,removed.length());
		}
		if(removed.substring(removed.length() - 1, removed.length()).equals(SystemConstants.QUOTE)) {
			removed = removed.substring(0,removed.length() - 1);
		}
		return removed;
	}

	@SuppressWarnings("unchecked")
	private String[] getPropertyValues(String propertyName) {
		
		String[] values = null;
		Expression matchTokenName = ExpressionFactory.matchExp(Token.TOKEN_NAME_PROPERTY, propertyName);
		List<Token> tokens = Token.getObjectByExpression(contextID.getContext(), Token.class, matchTokenName);
		if (tokens.isEmpty()) {
			return null;
		} else {
			values = tokenizeParameters(tokens.get(0).getTokenValue(), false, SystemConstants.COMMA); 
		}
		return values;
	}

	private static String[] tokenizeParameters(String parameters, boolean keepQuote, String delimiter) {
		final char QUOTE = SystemConstants.QUOTE.toCharArray()[0];
		final char BACK_SLASH = SystemConstants.BACK_SLASH.toCharArray()[0];
		char prevChar = 0;
		char currChar = 0;
		StringBuffer sb = new StringBuffer(parameters.length());

		if (!keepQuote) {
			for (int i = 0; i < parameters.length(); i++) {
				if (i > 0) {
					prevChar = parameters.charAt(i - 1);
				}
				currChar = parameters.charAt(i);

				if (currChar != QUOTE || (currChar == QUOTE && prevChar == BACK_SLASH)) {
					sb.append(parameters.charAt(i));
				}
			}

			if (sb.length() > 0) {
				parameters = sb.toString();
			}
		}
		return parameters.split(delimiter);
	}
	

}
