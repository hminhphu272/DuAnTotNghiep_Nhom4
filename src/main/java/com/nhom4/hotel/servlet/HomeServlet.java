package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.LoaiPhongDAO;
import com.nhom4.hotel.dao.PhongDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final PhongDAO phongDAO = new PhongDAO();
    private final LoaiPhongDAO loaiPhongDAO = new LoaiPhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("rooms", phongDAO.findAvailable(null, null, null));
        req.setAttribute("types", loaiPhongDAO.findActive());
        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
    }
}
