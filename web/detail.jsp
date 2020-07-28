<%-- 
    Document   : detail
    Created on : Jul 28, 2020, 7:20:19 AM
    Author     : Vinh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .card {
                box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
                max-width: 500px ;
                margin: auto;
                text-align: center;
                font-family: arial;
            }

            .price {
                color: red;
                font-size: 22px;
            }

            .card button {
                border: none;
                outline: 0;
                padding: 12px;
                color: white;
                background-color: #000;
                text-align: center;
                cursor: pointer;
                width: 100%;
                font-size: 18px;
            }

            .card button:hover {
                opacity: 0.7;
            }
        </style>
    </head>
    <body>
        <c:if test="${requestScope.DETAIL != null}" >
        <ul class="products">
            <div class="card">
                <img src="${requestScope.DETAIL.imgUrl}" style="width:100%">
                <h1>${requestScope.DETAIL.name}</h1>
                <p class="price">${requestScope.DETAIL.price} Mil VND</p>
                <p> Mil VND/m2:  ${requestScope.DETAIL.meanPrice}</p>
                <a href="${requestScope.DETAIL.webUrl}" target="_blank">Link Apartment</a>
                <p> Room  : ${requestScope.DETAIL.room}</p>
                <p>Rest Room: ${requestScope.DETAIL.restRoom}</p>
                <p>Address: ${requestScope.DETAIL.address}</p>
                <p>Area:  ${requestScope.DETAIL.area} m2</p>
                <p><button></button></p>
            </div>
        </ul>
        </c:if>
    </body>
</html>
