/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhl.controllers;

import vinhl.entities.ApartmentManager;
import vinhl.filter.ApartmentFilter;
import vinhl.jaxb.Apartment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import vinhl.dao.DistrictDAO;

/**
 * @author Vinh
 */
@WebServlet(name = "RecommendController", urlPatterns = {"/RecommendController"})
public class RecommendController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            //System.out.println("Hi Im in");
            float maxPrice = 0;
            int minArea = 0;
            int minRoom = 0;
            int minRest = 0;
            int wP = 0, wA = 0, wR = 0, wRe = 0;
            String district = request.getParameter("txtDistrict");
            if (!Objects.isNull(request.getParameter("txtPriceMax"))) {
                maxPrice = Float.valueOf(request.getParameter("txtPriceMax"));

            }

            
            if (!Objects.isNull(request.getParameter("txtAreaMin"))) {
                minArea = Integer.parseInt(request.getParameter("txtAreaMin"));

            }

            if (!Objects.isNull(request.getParameter("txtRoomMin"))) {
                minRoom = Integer.parseInt(request.getParameter("txtRoomMin"));

            }

            if (!Objects.isNull(request.getParameter("txtRestMin"))) {
                minRest = Integer.parseInt(request.getParameter("txtRestMin"));
            }
            
            if (!Objects.isNull(request.getParameter("txtPriceWeight"))) {
                wP = Integer.parseInt(request.getParameter("txtPriceWeight"));
            }
            if (!Objects.isNull(request.getParameter("txtAreaWeight"))) {
                wA = Integer.parseInt(request.getParameter("txtAreaWeight"));
            }
            if (!Objects.isNull(request.getParameter("txtRoomWeight"))) {
                wR = Integer.parseInt(request.getParameter("txtRoomWeight"));
            }
            if (!Objects.isNull(request.getParameter("txtRestWeight"))) {
                wRe = Integer.parseInt(request.getParameter("txtRestWeight"));
            }


            int id = DistrictDAO.getDistrictId(district);
            System.out.println("maxPrice" + maxPrice);
            System.out.println("minArea" + minArea);
            System.out.println("min Rest R" + minRest);
            System.out.println("weight" + wP);

            ApartmentFilter filter = new ApartmentFilter();
            filter.setMaxprice(maxPrice);
            filter.setMinArea(minArea);
            filter.setMinRoom(minRoom);
            filter.setMinRestRoom(minRest);
            filter.setWeightPrice(wP);
            filter.setWeightRoom(wR);
            filter.setWeightArea(wA);
            filter.setWeighRest(wRe);
            filter.setId(id);

            ApartmentManager am = new ApartmentManager();

            List<Apartment> result = am.getRecommend(filter, 3);
            System.out.println(result.size());
            request.setAttribute("RECOM_APA", result);
        } catch (Exception e) {
            log(e.getMessage());
        } finally {
            request.getRequestDispatcher("GetDistrictController").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
