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
            <h1 style="color: blue">Hello, We can show the shortest route ? </h1>
            <a>Show us your priority places !</a>
            </br>



            <form action="MainController" method="POST">

                <table border="0">
                    <tbody>
                        <tr>
                            <td>Address 1 :</td>
                            <td><input type="text" name="txtAdd1" required=""></td>
                        </tr>
                    </tbody>
                </table>
                <br/>


                <table border="0">
                    <tbody>
                        <tr>
                            <td>Address 2 :</td>
                            <td><input type="text" name="txtAdd2"></td>
                        </tr>
                    </tbody>
                </table>
                <br/>


                <table border="0">
                    <tbody>
                        <tr>
                           <td>Address 3 :</td>
                            <td><input type="text" name="txtAdd3"></td>
                        </tr>
                    </tbody>
                </table>
                <br/>


                <input type="submit" name="action" value="Click To Suggest Location">

            </form>   
        </section>


        <c:if test="${requestScope.LOC_APA != null}">
            <c:if test="${not empty requestScope.LOC_APA}">
                <ul class="products">
                    <c:forEach items="${requestScope.LOC_APA}" var="dto">
                        <li>
                            <form action="ViewDetailController" method="POST">
                                <img src="${dto.imgUrl}" style="width: 200px; height: 200px;">
                                <br/>
                                <span>
                                    ${dto.name}
                                </span>
                                <input type="hidden" name="uname" value=" ">
                                <br/>
                                <strong style="color: red;" >                             
                                    Total: ${dto.score} Kms                           
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
