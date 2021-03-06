/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhl.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Vinh
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String LOGIN = "LoginController";
    private static final String MANAGE_PRODUCT = "admin_product.jsp";
    private static final String CRAWL_NDN = "CrawlNhaDatNhanhController";
    private static final String CRAWL_NDS = "CrawlNhaDatSoController";
    private static final String SMART_RECOMMEND = "GetDistrictController";
    private static final String RECOMMEND = "RecommendController";
    private static final String MAIN_USER = "user.jsp";
    private static final String MAIN_ADMIN = "admin.jsp";
    private static final String REPORT = "ViewReportController";
    private static final String DETAIL = "ViewDetailController";

    private static final String LOCATION_CONTROLLER = "suggestLocation.jsp";
    private static final String LOCATION_SUGGEST = "LocationSuggestController";



    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            if (action.equals("Login")) {
                url = LOGIN;
            } else if (action.equals("View Best Location")) {
                url = LOCATION_CONTROLLER;
            } else if (action.equals("Manage Product")) {
                url = MANAGE_PRODUCT;
            } else if (action.equals("Crawl Nha Dat Nhanh")) {
                url = CRAWL_NDN;
            } else if (action.equals("Crawl Nha Dat So")) {
                url = CRAWL_NDS;
            } else if (action.equals("Click To Suggest")) {
                url = RECOMMEND;
            } else if (action.equals("View Report")) {
                url = REPORT;
            } else if (action.equals("Smart Recommend")) {
                url = SMART_RECOMMEND;
            } else if (action.equals("View Detail")) {
                url = DETAIL;
            } else if (action.equals("Click To Suggest Location")) {               
                url = LOCATION_SUGGEST;
            } 
            else {
                request.setAttribute("ERROR", "Action is not support");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
