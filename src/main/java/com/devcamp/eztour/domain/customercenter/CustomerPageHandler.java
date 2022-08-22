package com.devcamp.eztour.domain.customercenter;

import com.devcamp.eztour.domain.rvw.PageHandler;

public class CustomerPageHandler {
    private int totalCnt; // 총 게시물 갯수
    private int pageSize; // 한 페이지의 크기
    private int naviSize = 10; // 페이지 내비게이션의 크기
    private int totalPage; // 전체 페이지의 갯수
    private int page; // 현재 페이지
    private int beginPage; // 네비게이션 첫번째 페이지
    private int endPage; //네비게이션 마지막 페이지
    private boolean showPrev; // 이전 페이지의 이동 가능성 여부
    private boolean showNext; // 다음 페이지의 이동 가능성 여부

    public CustomerPageHandler(int totalCnt, int page) {
        this(totalCnt, page, 10);
    }



    public CustomerPageHandler(int totalCnt, int page, int pageSize) {
        this.totalCnt = totalCnt;
        this.page = page;
        this.pageSize = pageSize;

        totalPage = (int)Math.ceil(totalCnt /(double)pageSize);
        beginPage = (page-1) / naviSize * naviSize + 1;
        endPage = Math.min(beginPage + naviSize-1, totalPage);
        showPrev = beginPage != 1;
        showNext = endPage != totalPage;
    }

    void print() {
        System.out.println("page = " + page);
        System.out.print(showPrev ? "[PREV]" : "");
        for(int i = beginPage; i <= endPage; i++) {
            System.out.print(i+" ");
        }
        System.out.println(showNext ? " [NEXT]" : "");
    }

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNaviSize() {
        return naviSize;
    }

    public void setNaviSize(int naviSize) {
        this.naviSize = naviSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isShowPrev() {
        return showPrev;
    }

    public void setShowPrev(boolean showPrev) {
        this.showPrev = showPrev;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    @Override
    public String toString() {
        return "CustomerPageHandler{" +
                "totalCnt=" + totalCnt +
                ", pageSize=" + pageSize +
                ", naviSize=" + naviSize +
                ", totalPage=" + totalPage +
                ", page=" + page +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", showPrev=" + showPrev +
                ", showNext=" + showNext +
                '}';
    }
}
