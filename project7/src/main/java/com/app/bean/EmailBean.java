package com.app.bean;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahesh pardeshi
 *
 */
/** CLASS IS USED TO GENERATE EMAIL CONFIGURATION AND PASS IT AS AN INPUT TO EMAIL SENDER SERVICE */
public class EmailBean {

	private String emailSubject = "";

	private String emailBody = "";

	private List<String> recipients;

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String _emailSubject) {
		this.emailSubject = _emailSubject;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String _emailBody) {
		this.emailBody = _emailBody;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(ArrayList<String> _recipients) {
		this.recipients = _recipients;
	}

	public EmailBean addRecipient(String recipient) {
		if (this.recipients == null) {
			this.recipients = new ArrayList<String>();
		}
		if (this.recipients != null) {
			this.recipients.add(recipient);
		}
		return this;
	}

	public EmailBean removeRecipient(String recipient) {
		if (this.recipients != null) {
			this.recipients.remove(recipient);
		}
		return this;
	}

	public Integer sizeOfRecipients() {
		if (this.recipients != null) {
			return this.recipients.size();
		}
		return 0;
	}
}
