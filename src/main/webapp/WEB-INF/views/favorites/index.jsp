<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />

<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>日報管理システムへようこそ</h2>
        <h3>【自分のいいね　一覧】</h3>
         <table>
                <tbody>
                    <tr>
                        <th>氏名</th>
                        <th>登録日時</th>
                        <th>タイトル</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach var="favorite" items="${favorites}" varStatus="status">
                        <fmt:parseDate value="${favorite.createdAt}" pattern="yyyy-MM-dd'T'HH:mm" var="createdAt" type="date" />

                    <tr class="row${status.count % 2}">
                        <td class="report_name"><c:out value="${favorite.report.employee.name}" /></td>
                        <td class="created_at"><fmt:formatDate value='${createdAt}' pattern='yyyy-MM-dd HH:mm' /></td>
                        <td class="report_title">${favorite.report.title}</td>
                        <td class="report_action"><a href="<c:url value='?action=${actRep}&command=${commShow}&id=${favorite.report.id}' />">詳細を見る</a></td>
                    </tr>
                     </c:forEach>
                </tbody>
            </table>



        <p><a href="<c:url value='?action=${actRep}&command=${commNew}' />">新規日報の登録</a></p>
    </c:param>
</c:import>
