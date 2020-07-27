<%-- 
    Document   : smartRecommend
    Created on : Jul 27, 2020, 7:58:41 PM
    Author     : Vinh
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Smart Recommend</title>
        <style>
            .products {
                list-style-type: none;
            }

            .all {
                margin-left: 150px;
                margin-top: 75px;
                font-size: 20px;
            }
            .test {
                position: absolute;
                margin-left: 150px;
                margin-top: 75px;
                font-size: 20px;
                top:700px;
            }
            .products li {
                display: block;
                float: left;
                margin: 100px;
                width: 225px; 
                text-align: center; 
            }
        </style>
    </head>
    <body>
        


        <c:if test="${requestScope.ANA_DIS != null}">
            <c:if test="${not empty requestScope.ANA_DIS}">
                <table border = "1">
                    <thead>
                
                <tr>
                                    <th>Name</th>
                                    <th>Average Price</th>
                                    <th>Average Price/m2</th>
                                    <th>Average Area</th>
                                    <th>Range 95%</th>
                </tr>
            </thead>
                </table>
            
                <ul class="products">
                    <c:forEach items="${requestScope.ANA_DIS}" var="dto">
                        <table border="1">                        
                            <tbody>
                                <tr>
                                    <td> ${dto.name}</td>
                                    <td> ${dto.avgePrice} Million</td>
                                    <td> ${dto.avgMean}</td>
                                    <td> ${dto.avgArea} </td>
                                    <td> ${dto.rangePriL} - ${dto.rangePriU}  </td>
                                </tr>
                            </tbody>
                        </table>                     
                    </c:forEach>
                </ul>
            </c:if>
        </c:if>
    </body>
</html>
