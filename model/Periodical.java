package model;

import java.io.Serializable;

public class Periodical extends Publication implements Serializable {
	private String IssueNumber;
	private static int curID = 1;
	
	public Periodical(int issueNo, String title, int maxCheckoutLength) {
		super(title,maxCheckoutLength);
		this.IssueNumber = String.valueOf(issueNo);
	}
	
	public String getIssueNumber() {
		return IssueNumber;
	}

	public void setIssueNumber(String issueNo) {
		this.IssueNumber = issueNo;
	}

//	@Override
//	public boolean addCopy() {
//		// TODO Auto-generated method stub
//		LendableCopy copy = new LendableCopy(this);
//		return false;
//	}

	public static int getCurID() {
		return curID++;
	}
	
	@Override
	public String toString() {
		return "[PERIODICAL] " + super.toString() + ", curID: " + curID + ", IssueNumber: " + IssueNumber;
	}
}
