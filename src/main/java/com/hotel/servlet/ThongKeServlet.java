package com.hotel.servlet;

import com.hotel.dao.ThongKeDAO;
import com.hotel.util.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/report")
public class ThongKeServlet extends HttpServlet {
    private final ThongKeDAO dao = new ThongKeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AuthUtil.hasRole(req, "QUAN_LY")) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.setAttribute("doanhThu", dao.doanhThu());
        req.setAttribute("phong", dao.thongKePhong());
        req.setAttribute("datPhong", dao.thongKeDatPhong());
        req.getRequestDispatcher("/views/thongke/report.jsp").forward(req, resp);
    }
}
