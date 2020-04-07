package opcon.commvault.connector.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import opcon.commvault.connector.interfaces.IJobExecutionResult;
import opcon.commvault.connector.modules.JobExecutionResult;
import opcon.commvault.connector.util.CommVaultUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJobExecutorImpl {

	
	private final static Logger LOG = LoggerFactory.getLogger(AbstractJobExecutorImpl.class);
	private CommVaultUtilities _CommVaultUtilities = new CommVaultUtilities();

	protected ScheduledExecutorService executorStart = Executors.newScheduledThreadPool(10);
	protected ScheduledFuture<?> futureStart = null;
	
	protected Hashtable<String, IJobExecutionResult> htblJobResults = new Hashtable<String, IJobExecutionResult>(); 
	protected Collection<String> jobNameList = new ArrayList<String>(); 
	protected boolean initialStatus = true;
	protected boolean jobGroup = false;
	
	protected String outputFileName = null;
	protected FileWriter fwOutputFile = null;
	protected BufferedWriter bwOutputFile = null;
	protected Connection conn = null;

	protected IJobExecutionResult result = new JobExecutionResult();
	protected boolean jobFinished = false;
	protected String currentJobDescription = null;

	public synchronized void waitForJobCompletion(
			) throws Exception {
		
		try {
			while(!jobFinished) {
				wait();
			}
		} catch (InterruptedException ex) {
			throw new Exception(ex);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	} 	// END : waitForJobCompletion

	protected synchronized void jobFinished(
			) throws Exception {
		
		try {
			jobFinished = true;
			notify();
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	} 	// END : jobFinished

	public int startJob(
			) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	protected void getJobStatus(
			final int jobId, 
			int initialPollDelay, 
			int pollDelay
			) throws Exception {
		try {
		    final Runnable checkJobStatus = new Runnable() {
		         public void run() { 
		        	 try {
						getStatus(jobId);
					} catch (Exception ex) {
						LOG.error(_CommVaultUtilities.getExceptionDetails(ex));
					} 
		         }
		       };
		       futureStart = executorStart.scheduleWithFixedDelay(checkJobStatus, initialPollDelay, pollDelay, TimeUnit.SECONDS);				
			jobFinished = false;;
			waitForJobCompletion();
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	} // END : getJobStartStatus
	
	/**
	 * @param runid
	 * @param repository
	 * @throws SMAException
	 */
	abstract void getStatus(
			int jobId
			) throws Exception;

}

