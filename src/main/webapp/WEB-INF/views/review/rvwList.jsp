<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>fastcampus</title>
    <link rel="stylesheet" href="<c:url value='/css/rvw/rvwList.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
<div class="wrap">
    <div class="content">
        <div class="tit">
            <h1>Review</h1>
        </div>
        <div class="navi">
            <div class="menu">
                <div class="nation_1">
                    <span class="menu_name" onclick="">전체</span>
                    <span class="menu_name" onclick="">동남아</span>
                    <span class="menu_name" onclick="">동아시아</span>
                    <span class="menu_name" onclick="">동유럽</span>
                    <span class="menu_name" onclick="">서유럽</span>
                    <span class="menu_name" onclick="">오세아니아</span>
                </div>
                <!--div class="nation_2">
                    <span class="menu_name" onclick="">남아프리카</span>
                    <span class="menu_name" onclick="">북아프리카</span>
                    <span class="menu_name" onclick="">중남미</span>
                    <span class="menu_name" onclick="">동남아</span>
                    <span class="menu_name" onclick="">북유럽</span>
                    <span class="menu_name" onclick="">중동</span>
                </div-->
            </div>
        </div>
        <div class="searchArea">
            <div class="searchBox">
                <form action="<c:url value="/review/list"/>" class="search-form" method="get">
                    <select class="searchOption" name="option">
                        <option value="A" ${ph.sc.option=='A' || ph.sc.option=='' ? "selected" : ""}>제목+내용</option>
                        <option value="T" ${ph.sc.option=='T' ? "selected" : ""}>제목만</option>
                        <option value="W" ${ph.sc.option=='W' ? "selected" : ""}>작성자</option>
                    </select>

                    <input class="searchInput" type="text" name="keyword" value="${ph.sc.keyword}" placeholder="검색어를 입력해주세요">
                    <input class="searchButton" type="submit" value="검색">
                </form>
            </div>
        </div>
        <button type="button" id="writeBtn" onclick="location.href='<c:url value="/review/write"/>'">작성하기</button>
        <div class="highArea">
            <a class="totalCnt">
                총 ${totalCnt}건
            </a>
            <div class="sort">
                <span class="sort_name" style="margin-right: 30px;" onclick="">최신순</span>
                <span class="sort_name" style="margin-right: 30px;" onclick="">오래된순</span>
                <span class="sort_name" style="margin-right: 30px;" onclick="">가장 많은 좋아요</span>
                <span class="sort_name" onclick="">가장 많은 조회수</span>
            </div>
        </div>
        <div class="reviewArea">
            <c:forEach var="rvwDto" items="${list}">
                <section class="reviewDetail"><a href="<c:url value='/review/read${ph.sc.queryString}&rvw_no=${rvwDto.rvw_no}'/>">${rvwDto.rvw_ttl}</a>
                    <div class="reviewImg">
                        <img src="${rvwDto.img_pth}">
                    </div>
                    <div class="reviewInfo">
                        <div class="reviewTitle">
                            <br>
                            <span class="reviewTitle-rvw_ttl">${rvwDto.rvw_ttl}</span>
                        </div>
                        <div class="reviewContent">
                            <span class="reviewContent-rvw_cont">${rvwDto.rvw_cont}</span>
                        </div>
                        <div class="reviewWriter">
                            <span class="reviewWriter-wrt_nm">작성자 | ${rvwDto.wrt_nm}</span>
                        </div>
                        <div class="reviewRegisterDate">
                            <span class="reviewRegisterDate-rvw_reg_date"><fmt:formatDate value="${rvwDto.rvw_reg_date}" pattern="yyyy-MM-dd hh:mm:ss"/></span>
                        </div>
                        <div class="reviewLikeViewCnt">
                            <br>
                            <span class="reviewLikeViewCnt-lk_cnt-rvw_cont" style="margin-left: 50px">좋아요 ${rvwDto.lk_cnt}</span>
                            <br>
                            <br>
                            <span class="reviewLikeViewCnt-lk_cnt-rvw_cont" style="margin-left: 50px">조회수 ${rvwDto.rvw_vcnt}</span>
                        </div>
                    </div>
                </section>
            </c:forEach>
            <div class="pagingContainer">
                <div class="paging">
                    <c:if test="${totalCnt==null || totalCnt==0}"> <!-- 페이지 핸들러추후 작업 필요-->
                        <div> 게시물이 없습니다. </div>
                    </c:if>
                    <c:if test="${totalCnt!=null && totalCnt!=0}">
                        <c:if test="${ph.showPrev}">
                            <a class="page" href="<c:url value="/review/list${ph.sc.getQueryString(ph.beginPage-1)}"/>">&lt;</a>
                        </c:if>
                        <c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
                            <a class="page ${i==ph.sc.page? "paging-active" : ""}" href="<c:url value="/review/list${ph.sc.getQueryString(i)}"/>">${i}</a>
                        </c:forEach>
                        <c:if test="${ph.showNext}">
                            <a class="page" href="<c:url value="/review/list${ph.sc.getQueryString(ph.endPage+1)}"/>">&gt;</a>
                        </c:if>
                    </c:if>
                </div>
            </div>
        </div>
        <div>
        </div>
    </div>
</div>
</body>
</html>