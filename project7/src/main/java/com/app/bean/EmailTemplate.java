package com.app.bean;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mahesh pardeshi
 *
 */
/** CLASS IS USED TO GENERATE EMAIL TEMPLATE USING VELOCITY TEMPLATE LIBRARY AND WRAP THE DYNAMIC INPUTS WITH KEYS AND USED IN EMAIL SENDER SERVICE */
public class EmailTemplate {

	private String templateId;

	private Map<String, Object> references = new HashMap<String, Object>();

	public void addReference(String referenceKey, Object referenceValue) {
		references.put(referenceKey, referenceValue);
	}

	public void addReference(String referenceKeyValue) {
		String[] reference = referenceKeyValue.split(",");
		String referenceKey = reference[0];
		String referenceValue = reference[1];
		references.put(referenceKey, referenceValue);
	}

	public Map<String, Object> getReferences() {
		return references;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

}
