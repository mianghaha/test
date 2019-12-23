package compare;

public class JspActInfo implements Comparable<JspActInfo>{
	
	public JspActInfo(long id, int index, String url){
		this.id = id;
		this.index = index;
		this.url = url;
	}

	private long id;
	private int index;
	private String url;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public int compareTo(JspActInfo o) {
		// TODO Auto-generated method stub
		if(this.index > o.index){
			return 1;
		}else if(this.index == o.index){
			if(this.id > o.getId()){
				return 1;
			}
		}
		return -1;
	}
	
}
