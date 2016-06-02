package com.app.server.service.datavisualizer.searchengine;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class QueryParameterPreparer {

	private final String queryCriteria[] = { ".*(last)[ ]+(\\d+)[ ]+(week)*", ".*(last)[ ]+(\\d+)[ ]+(month)*", ".*(last)[ ]+(\\d+)[ ]+(year)*", ".*(last)[ ]+(\\d+)[ ]+(day)*" };

	public String getDateTimeCriteria(String query, Object sessionData) throws Exception {
		Pattern pattern = null;
		String finalQuery = new String(query);
		Matcher matcher = null;
		String finalDate = null;
		for (int i = 0; i < queryCriteria.length; i++) {
			pattern = Pattern.compile(queryCriteria[i], Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(query);
			while (matcher.find()) {
				String lst = matcher.group(1);
				String number = matcher.group(2);
				String time = matcher.group(3);

				if (lst != null && number != null && time != null) {
					switch (time.toLowerCase()) {
					case "day":
						finalDate = subtractDays(Integer.parseInt(number.trim()));
						finalQuery = replaceManString(lst, number, time + "s|" + time, finalQuery);
						break;
					case "week":
						finalDate = subtractWeek(Integer.parseInt(number.trim()));
						finalQuery = replaceManString(lst, number, time + "s|" + time, finalQuery);
						break;
					case "month":
						finalDate = subtractMonth(Integer.parseInt(number.trim()));
						finalQuery = replaceManString(lst, number, time + "s|" + time, finalQuery);
						break;
					case "year":
						finalDate = subtractYear(Integer.parseInt(number.trim()));
						finalQuery = replaceManString(lst, number, time + "s|" + time, finalQuery);
						break;

					default:
						finalDate = subtractMonth(Integer.parseInt(number.trim()));
						break;
					}
				}
				break;
			}

		}
		if (finalDate == null) {
			finalDate = subtractMonth(12);
		}
		JSONObject jsonObject = new JSONObject();
		
		
		String finalStr = (finalQuery != null && finalQuery.trim().length() == 0) ? "*:*" : finalQuery.trim();
		JSONObject finalFQ=getGTandLtConditionCheck(finalStr);
		
		String addOnFQString="";
		if(finalFQ!=null)
		{
			finalStr=finalFQ.getString("Q");
			addOnFQString=finalFQ.getString("FQ");
		}
		/**User Access Condition Added	**/	
		if (sessionData!=null){
			addOnFQString =addOnFQString+ " "+sessionData;
		}
		jsonObject.put("FQ", "&fq=((updatedDate:[" + finalDate + "%20TO%20*]%20) "+addOnFQString + ") OR reportid:\"-1\"");
		jsonObject.put("Q", finalStr);
		
		return jsonObject.toString();

	}
	public String replaceManString(String last, String number, String time, String baseString) {
		baseString = baseString.replaceAll(last, "");
		baseString = baseString.replaceAll(number, "");
		baseString = baseString.replaceAll(time, "");

		return baseString;
	}
	private String subtractDays(Integer days) {
		Calendar now = Calendar.getInstance();

		now.add(Calendar.DATE, -days);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;

		return sdf.format(now.getTime());

	}

	private String subtractWeek(Integer week) {
		Calendar now = Calendar.getInstance();

		now.add(Calendar.DATE, -(week * 7));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;

		return sdf.format(now.getTime());
	}

	private String subtractMonth(Integer month) {
		Calendar now = Calendar.getInstance();

		now.add(Calendar.MONTH, -(month));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;

		return sdf.format(now.getTime());
	}

	private String subtractYear(Integer year) {
		Calendar now = Calendar.getInstance();

		now.add(Calendar.YEAR, -(year));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;

		return sdf.format(now.getTime());
	}
	private JSONObject getGTandLtConditionCheck(String baseString) throws Exception
	{
		String myString=new String(baseString);
		String queryCriteria[] = { " (.*?)( > )(\\d+).*", " (.*?)( >= )(\\d+).*", " (.*?)( < )(\\d+).*", " (.*?)( <= )(\\d+).*" };
		StringBuilder fqStr=new  StringBuilder();
		for(int i=0;i<queryCriteria.length;i++)
		{
			String str=getGtLtString(myString,queryCriteria[i]);
			if(str!=null && str.length()>0)
			{
				String[] strArray=str.split("::");
				if(strArray.length==3)
				{
					fqStr.append(strArray[0]+":");
					if(i<=1)	
					{
						fqStr.append("[ "+strArray[2]+ " TO *]");
					}else{
						fqStr.append("[ * TO "+strArray[2]+" ]");
					}
					myString=myString.replace(strArray[0], "");
					myString=myString.replace(strArray[1], "");
					myString=myString.replace(strArray[2], "");
				}
			}			
		}
		
		JSONObject json=null;
		
		if(fqStr!=null && fqStr.length()>0)
		{
			json=new JSONObject();
			json.put("FQ", " AND " + fqStr.toString());
			json.put("Q", myString);
			
		}
		return json;
	}
	private String getGtLtString(String baseString, String regExp) {
		Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
		StringBuilder stringBuild = new StringBuilder();
		Matcher matcher = pattern.matcher(baseString);
		while (matcher.find()) {
			if (matcher.group(1) != null) {
				stringBuild.append(matcher.group(1));
			}
			if (matcher.group(2) != null) {
				stringBuild.append("::" + matcher.group(2));
			}
			if (matcher.group(3) != null) {
				stringBuild.append("::" + matcher.group(3));
			}
			if (stringBuild != null && stringBuild.length() > 0) {
				String[] strLen = stringBuild.toString().split("::");
				if (strLen.length == 3) {
					return stringBuild.toString();
				} else {
					return null;
				}
			}
		}
		return null;
	}

}
