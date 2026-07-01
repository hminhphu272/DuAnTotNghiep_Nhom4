package com.hotel.servlet;

import com.hotel.dao.PhongDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final PhongDAO phongDAO = new PhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        req.setAttribute("rooms", phongDAO.findAll(keyword));
        req.setAttribute("keyword", keyword == null ? "" : keyword);
        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
    }
}
