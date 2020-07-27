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
        <section class="all">
            <h1 style="color: blue">Hello, Can I Help You ? </h1>
            <a>Show us your priority. We will show you the top choices !</a>
            </br>



            <form action="MainController" method="POST">

                <select name="txtDistrict">
                    <c:forEach  items="${requestScope.RESULTDISTRICT}" var="ds">
                        <option value="${ds}">${ds}</option>
                    </c:forEach>
                </select>


                <br/>

                Show us how maximum you can afford? (Mil VND)
                <table border="0">
                    <tbody>
                        <tr>
                            <td>Max Price :</td>
                            <td><input type="number" name="txtPriceMax" required=""></td>
                        </tr>
                        <tr>
                            <td>Weight: </td>
                            <td><input type="number" name="txtPriceWeight" value="0" required=""></td>
                        </tr>
                    </tbody>
                </table>
                <br/>


                How minimum large your ideal home? (optional)
                <table border="0">
                    <tbody>
                        <tr>
                            <td>Min Area :</td>
                            <td><input type="number" name="txtAreaMin" min="1"></td>
                        </tr>
                        <tr>
                            <td>Weight: </td>
                            <td><input type="number" name="txtAreaWeight" value="0"></td>
                        </tr>
                    </tbody>
                </table>
                <br/>


                How many rooms at least you want? (optional)
                <table border="0">
                    <tbody>
                        <tr>
                            <td>Min Room :</td>
                            <td><input type="number" name="txtRoomMin" min="0"></td>
                        </tr>
                        <tr>
                            <td>Weight: </td>
                            <td><input type="number" name="txtRoomWeight" value="0"></td>
                        </tr>
                    </tbody>
                </table>
                <br/>


                How many rest rooms at least you want? (optional)
                <table border="0">
                    <tbody>
                        <tr>
                            <td>Min Rest Room :</td>
                            <td><input type="number" name="txtRestMin" min="1"></td>
                                <%--                            <td>Max Rest Room :</td>--%>
                                <%--                            <td><input type="number" name="txtRestMax" min="2"></td>--%>
                        </tr>
                        <tr>
                            <td>Weight: </td>
                            <td><input type="number" name="txtRestWeight" value="0"></td>
                        </tr>
                    </tbody>
                </table>
                <br/>

                <input type="submit" name="action" value="Click To Suggest">

            </form>   
        </section>


        <c:if test="${requestScope.RECOM_APA != null}">
            <c:if test="${not empty requestScope.RECOM_APA}">
                <ul class="products">
                    <c:forEach items="${requestScope.RECOM_APA}" var="dto">
                        <li>
                            <form action="ViewDetailController" method="POST">
                                <img src="${dto.imgUrl}" style="width: 200px; height: 200px;">
                                <span>
                                    ${dto.name}
                                </span>
                                <input type="hidden" name="uname" value=" ">
                                <br/>
                                <strong style="color: red;" >                             
                                    ${dto.score} %                              
                                </strong>
                                <br/>
                                <input type="submit" name="View Detail" value="View Details" />
                            </form>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </c:if>
    </body>
</html>
