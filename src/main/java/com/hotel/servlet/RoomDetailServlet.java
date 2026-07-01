package com.hotel.servlet;

import com.hotel.dao.PhongDAO;
import com.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/room/detail")
public class RoomDetailServlet extends HttpServlet {
    private final PhongDAO dao = new PhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = ParamUtil.getInt(req, "id");
        req.setAttribute("room", dao.findById(id));
        req.getRequestDispatcher("/views/phong/detail.jsp").forward(req, resp);
    }
}
