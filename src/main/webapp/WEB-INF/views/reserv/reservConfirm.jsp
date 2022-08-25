<%--
  Created by IntelliJ IDEA.
  User: hka
  Date: 2022-08-12
  Time: 오후 9:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <link rel="stylesheet" href="<c:url value='/css/reserv/reserv_confirm.css'/>">
</head>
<body>
<div class="rc_big_box">
    <div class="rc_middle_box">
        <h1 class="dv_main_header">예약완료</h1>
        <div>
            <p>상품예약이 정상적으로 완료되었습니다.</p>
            <p>이지투어 상품을 예약해 주셔서 감사합니다.</p>
            <p>담당자 확인 후 해피콜 시 예약 승인여부를 알려드립니다.</p>
            <p>확정 불가 시 즉시 결제 내역은 자동취소처리 됩니다.</p>
        </div>
    </div>
    <div class="rc_reserv_dtl_box">
        <h2 class="rc_header">예약상세 내역</h2>
        <div class="rc_content_box">
            <p class="rc_content_box_header">${rcid.prd_nm}</p>
            <div>
                <span class="arp_code">${rcid.go_dpr_arl_id}</span>
                <span>${rcid.go_dpr_tm}</span> ~
                <span class="arp_code">${rcid.cb_arr_arl_id}</span>
                <span>${rcid.cb_arr_tm}</span>
            </div>
            <div class="rc_dtl_content_box">
                <div class="rc_low">
                    <dl class="rc_col1">
                        <dt>예약일</dt>
<%--                        <c:set var="today" value="${rcid.rsvt_date}" />--%>
<%--                        <dd><fmt:parseDate value="${today}" timeStyle="yyyy/MM/dd(E)"/></dd>--%>
                        <dd>${rcid.rsvt_date}</dd>
                    </dl>
                    <dl class="rc_col2">
                        <dt>예약번호</dt>
                        <dd>${rcid.rsvt_no}</dd>
                    </dl>
                </div>
                <div class="rc_low">
                    <dl class="rc_col1">
                        <dt>상품번호</dt>
                        <dd>${rcid.prd_cd}</dd>
                    </dl>
                    <dl class="rc_col2">
                        <dt>행사번호</dt>
                        <dd>${rcid.prd_dtl_cd}</dd>
                    </dl>
                </div>
                <div class="rc_low">
                    <dl class="rc_col1">
                        <dt>여행기간</dt>
                        <dd>${rcid.trv_per}</dd>
                    </dl>
                    <dl class="rc_col2">
                        <dt>출발인원</dt>
                        <dd>총 ${rcid.adt_cnt + rcid.chd_cnt + rcid.bb_cnt}명</dd>
                    </dl>
                </div>
                <div class="rc_low">
                    <dl class="rc_col1">
                        <dt>상품금액</dt>
                        <dd>${rcid.sum_prc}</dd>
                    </dl>
                    <dl class="rc_col2">
                        <dt>처리상태</dt>
                        <dd>${rcid.cmn_cd_rsvt_stt}</dd>
                    </dl>
                </div>
            </div>
        </div>
    </div>
    <div class="rc_reserv_dtl_box">
        <h2 class="rc_header">여행자 정보</h2>
        <div class="rc_reserv_dtl_subbox">
            <c:forEach var="trvlrInfo" items="${tid}" begin="0" end="${tid.size()}">
                <div class="rc_trvlr_nm">${trvlrInfo.trvlr_nm}</div>
                <div class="rc_trvlr_info_box">
                    <c:set var="trvlr_en_nm" value="${trvlrInfo.trvlr_en_nm}"/>
                    <div class="rc_trvlr_en_nm">- ${empty trvlr_en_nmm ? "영문명" : trvlr_en_nm}</div>
                    <div class="rc_trvlr_sub_box">
                        <span class="rc_trvlr_prc_title">- 상품가 </span>
                        <span class="rc_trvlr_prc">${trvlrInfo.pay_ftr_prc}</span>
                    </div>
                </div>
            </c:forEach>
            <div></div>
        </div>
    </div>
    <div class="rc_btn_box">
        <button class="home rc_btn">메인으로</button>
        <button id="rsvtCheck" class="rc_btn">예약/결제 조회</button>
    </div>
</div>

<script>
    $(document).ready(function(){
        $('.home').on("click", function(){
            location.href = '<c:url value="/"/>'
        });

        $('#rsvtCheck').on("click", function(){
            location.href = '<c:url value="/reserv/list"/>';
        });
    })
</script>
</body>
</html>
