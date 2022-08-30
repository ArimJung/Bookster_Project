package vo;

public class LlikeVO {

	private int lid;
	private String mid;
	private int oid;
	private int bid;
	private int rid;
	private int lstatus;
	private int nlstatus;
	private int report;
	
	
	
	
	
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public int getLstatus() {
		return lstatus;
	}
	public void setLstatus(int lstatus) {
		this.lstatus = lstatus;
	}
	public int getNlstatus() {
		return nlstatus;
	}
	public void setNlstatus(int nlstatus) {
		this.nlstatus = nlstatus;
	}
	public int getReport() {
		return report;
	}
	public void setReport(int report) {
		this.report = report;
	}
	@Override
	public String toString() {
		return "LlikeVO [lid=" + lid + ", mid=" + mid + ", oid=" + oid + ", bid=" + bid + ", rid=" + rid + ", lstatus="
				+ lstatus + ", nlstatus=" + nlstatus + ", report=" + report + "]";
	}
	
	
}
