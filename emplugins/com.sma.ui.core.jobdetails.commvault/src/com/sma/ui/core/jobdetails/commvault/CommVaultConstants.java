package com.sma.ui.core.jobdetails.commvault;

public interface CommVaultConstants {

	/**
	 * Constants shared by all Windows Jobs
	 */
	int COMMAND_LINE_LIMIT = 4000;

	String INVALID_COMMAND_LINE = "Invalid command line, please go back to the WINDOWS details to fix the command line.";
	String TOO_LONG_COMMAND_LINE = "Invalid command line, total length exceeds " + COMMAND_LINE_LIMIT + " characters.";
	String PARSE_JOB_COMMAND_ERROR = "Cannot parse the command line, this does not look like a valid {0}.";
	String TEXTBOX_CANNOT_BE_EMPTY = "{0} cannot be empty.";
	String XML_TEMPLATE_MUST_END_IN_XML = "{0} must end with .xml suffix.";
	String OTHER_OPTIONS = "Other Options";
	String XML_TEMPLATE_SUFFIX = ".xml";

	/**
	 * Constants for OpCon API
	 */

	String[] BACKUP_TYPES = new String[] { "DIFFERENTIAL", "INCREMENTAL", "FULL", "PRE_SELECT","SYNTHETIC_FULL" };
	String BACKUP_TYPES_PROPERTY_NAME = "CV_BACKUP_TYPES";

	String[] BACKUP_FILE_NAMES = new String[] { "VMWARE_GRANULAR_FULL.xml" };
	String BACKUP_FILE_NAMES_PROPERTY_NAME = "CV_BACKUP_FILE_NAMES";

	String JOB_DEFINITION_TAB_NAME = "Job Definition";
	String LOCATION_PATH_TOKEN = "[[CommvaultPath]]";
	String LOCATION_PATH_NAME = "Connector Path";
	String LOCATION_PATH_TOKEN_NAME_TOOLTIP = "The name of a global property that contains the installed location of the CommVault Connector";
	String BACKUPSET_NAME_NAME = "Backupset Name";
	String BACKUPSET_NAME_TOOLTIP = "(Required) the name of the backup set associated with the backup job - this will be replaced in the XML temmplate.";
	String CLIENT_NAME_NAME = "Client Name";
	String CLIENT_NAME_TOOLTIP = "(Required) the name of the client associated with the backup job - this will be replaced in the XML temmplat.e";
	String SUB_CLIENT_NAME_NAME = "Sub Client Name";
	String SUB_CLIENT_NAME_TOOLTIP = "The name of the subclient associated with the backup job - this will be replaced in the XML temmplate.";
	String INSTANCE_NAME = "Instance";
	String INSTANCE_TOOLTIP = "The name of the instance associated with the backup job - this will be replaced in the XML temmplate.";
	String BACKUP_TYPE_NAME = "Backup Type";
	String BACKUP_TYPE_TOOLTIP = "(Required) the name of the backup type associated with the backup job - this will be replaced in the XML temmplate.";
	String XML_TEMPLATE_NAME = "XML Template Name";
	String XML_TEMPLATE_TOOLTIP = "(Required) The name of the job to add to the schedule";
	
	String FAILURE_CRITERIA_TAB_NAME = "Failure Criteria";

	// CommVault Arguments
	String BACKUPSET_NAME_ARGUMENT = "bsn";
	String BACKUP_TYPE_ARGUMENT = "t";
	String XML_TEMPLATE_ARGUMENT = "xt";
	String CLIENT_NAME_ARGUMENT = "c";
	String SUBCLIENT_NAME_ARGUMENT = "sc";
	String INSTANCE_ARGUMENT = "i";

}
