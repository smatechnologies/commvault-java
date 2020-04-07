package opcon.commvault.connector.modules;

public enum CommVaultJobStatus {

	COMPLETED(0),
	COMPLETED_WITH_WARNINGS(1),
	COMPLETED_WITH_ERRORS(2),
	FAILED(3),
	FAILED_TO_START(4),
	RUNNING(5),
	WAITING(6),
	PENDING(7),
	SUSPEND(8),
	SUSPENDED(9),
	KILL_PENDING(10),
	INTERRUPT_PENDING(11),
	INTERRUPTED(12),
	QUEUED(13),
	RUNNING_CANNOT_BE_VERIFIED(14),
	ABNORMAL_TERMINATED_CLEANUP(15),
	COMMITTED(16),
	KILLED(17),
	WEB_SERVER_ERROR(18);

	private final int _type;

	/**
	 * Constructor.
	 * 
	 * @param value
	 */
	private CommVaultJobStatus(int type) {
		this._type = type;
	}

	public int getId() {
		return _type;
	}
	
	/**
	 * Get the AMT JOb Status according to the given value.
	 * 
	 * @param value
	 * @return
	 * @throws InvalidParameterException
	 *             if the value is not found
	 */
	public static CommVaultJobStatus fromValue(int value) throws Exception {
		CommVaultJobStatus[] jobStatusArray = CommVaultJobStatus.values();
		for (int i = 0; i < jobStatusArray.length; i++) {
			if (value == jobStatusArray[i].getId()) {
				return jobStatusArray[i];
			}
		}

		throw new Exception("Constant Not Found");
	}

	@Override
	public String toString() {
		switch (this) {

			case COMPLETED:
				return "Completed";
	
			case COMPLETED_WITH_WARNINGS:
				return "Completed w/ one or more warnings";
	
			case COMPLETED_WITH_ERRORS:
				return "Completed w/ one or more errors";
	
			case FAILED:
				return "Failed";
	
			case FAILED_TO_START:
				return "Failed to Start";
	
			case RUNNING:
				return "Running";
	
			case WAITING:
				return "Waiting";
	
			case PENDING:
				return "Pending";
	
			case SUSPEND:
				return "Suspend";
	
			case SUSPENDED:
				return "Suspended";
	
			case KILL_PENDING:
				return "Kill Pending";
	
			case INTERRUPT_PENDING:
				return "Interrupt Pending";
	
			case INTERRUPTED:
				return "Interrupted";
	
			case QUEUED:
				return "Queued";
	
			case RUNNING_CANNOT_BE_VERIFIED:
				return "Running (cannot be verified)";
	
			case ABNORMAL_TERMINATED_CLEANUP:
				return "Abnormal Terminated Cleanup";
	
			case COMMITTED:
				return "Committed";
	
			case KILLED:
				return "KIlled";
			
			case WEB_SERVER_ERROR:
				return "Web Server Error";
	
			default:
				return "Undefined";
		}
	}

	/**
	 * @param value
	 * @return
	 */
	public static int getIntValue(String value) {
		
		if(value.equalsIgnoreCase("Completed")) {
			return CommVaultJobStatus.COMPLETED.getId();
		} else if(value.equalsIgnoreCase("Completed w/ one or more warnings")) {
			return CommVaultJobStatus.COMPLETED_WITH_WARNINGS.getId();
		} else if(value.equalsIgnoreCase("Completed w/ one or more errors")) {
			return CommVaultJobStatus.COMPLETED_WITH_ERRORS.getId();
		} else if(value.equalsIgnoreCase("Failed")) {
			return CommVaultJobStatus.FAILED.getId();
		} else if(value.equalsIgnoreCase("Failed to Start")) {
			return CommVaultJobStatus.FAILED_TO_START.getId();
		} else if(value.equalsIgnoreCase("Running")) {
			return CommVaultJobStatus.RUNNING.getId();
		} else if(value.equalsIgnoreCase("Waiting")) {
			return CommVaultJobStatus.WAITING.getId();
		} else if(value.equalsIgnoreCase("Pending")) {
			return CommVaultJobStatus.PENDING.getId();
		} else if(value.equalsIgnoreCase("Suspend")) {
			return CommVaultJobStatus.SUSPEND.getId();
		} else if(value.equalsIgnoreCase("Suspended")) {
			return CommVaultJobStatus.SUSPENDED.getId();
		} else if(value.equalsIgnoreCase("Kill Pending")) {
			return CommVaultJobStatus.KILL_PENDING.getId();
		} else if(value.equalsIgnoreCase("Interrupt Pending")) {
			return CommVaultJobStatus.INTERRUPT_PENDING.getId();
		} else if(value.equalsIgnoreCase("Interrupted")) {
			return CommVaultJobStatus.INTERRUPTED.getId();
		} else if(value.equalsIgnoreCase("Queued")) {
			return CommVaultJobStatus.QUEUED.getId();
		} else if(value.equalsIgnoreCase("Running (cannot be verified)")) {
			return CommVaultJobStatus.RUNNING_CANNOT_BE_VERIFIED.getId();
		} else if(value.equalsIgnoreCase("Abnormal Terminated Cleanup")) {
			return CommVaultJobStatus.ABNORMAL_TERMINATED_CLEANUP.getId();
		} else if(value.equalsIgnoreCase("Committed")) {
			return CommVaultJobStatus.COMMITTED.getId();
		} else if(value.equalsIgnoreCase("KIlled")) {
			return CommVaultJobStatus.KILLED.getId();
		} else if(value.equalsIgnoreCase("Web Server Error")) {
			return CommVaultJobStatus.WEB_SERVER_ERROR.getId();
		} else {
			return 100;
		}
	}

}
