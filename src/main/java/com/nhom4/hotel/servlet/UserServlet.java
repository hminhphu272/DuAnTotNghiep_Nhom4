package com.nhom4.hotel.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.nhom4.hotel.dao.NguoiDungDAO;
import com.nhom4.hotel.entity.NguoiDung;

@WebServlet("/admin/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        if (action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            nguoiDungDAO.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        request.setAttribute("userList", nguoiDungDAO.getAll());
        request.getRequestDispatcher("/views/user-management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");

        NguoiDung nd = new NguoiDung();
        nd.setHoTen(request.getParameter("hoTen"));
        nd.setEmail(request.getParameter("email"));
        nd.setVaiTro(request.getParameter("vaiTro"));

        if ("add".equals(action)) {
            nguoiDungDAO.add(nd);
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            nd.setId(id);
            nguoiDungDAO.update(nd);
        }

        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}