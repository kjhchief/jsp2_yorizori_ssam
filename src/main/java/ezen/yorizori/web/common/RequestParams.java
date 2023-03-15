package ezen.yorizori.web.common;

/**
 * 여러개의 요청파라메터들을 포장하기 위한 자바빈
 */
public class RequestParams {
	private int requestPage;    /** 사용자 요청 페이지 */
	private int elementSize;    /** 페이지에 보여지는 목록 개수 */
	private int pageSize;       /** 페이지에 보여지는 페이지 개수 */
	private String search;      /** 검색값 */
	
	public RequestParams() {
		this(1, 10, 5, null);
	}
	
	public RequestParams(int requestPage, int elementSize, int pageSize, String search) {
		this.requestPage = requestPage;
		this.elementSize = elementSize;
		this.pageSize = pageSize;
		this.search = search;
	}

	public int getRequestPage() {
		return requestPage;
	}

	public void setRequestPage(int requestPage) {
		this.requestPage = requestPage;
	}

	public int getElementSize() {
		return elementSize;
	}

	public void setElementSize(int elementSize) {
		this.elementSize = elementSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	@Override
	public String toString() {
		return "RequestParams [requestPage=" + requestPage + ", elementSize=" + elementSize + ", pageSize=" + pageSize
				+ ", search=" + search + "]";
	}
	
}
