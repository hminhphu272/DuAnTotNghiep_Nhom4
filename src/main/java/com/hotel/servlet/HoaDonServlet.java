package com.hotel.servlet;

import com.hotel.dao.HoaDonDAO;
import com.hotel.util.AuthUtil;
import com.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/invoice/detail")
public class HoaDonServlet extends HttpServlet {
    private final HoaDonDAO dao = new HoaDonDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AuthUtil.isStaff(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int datPhongId = ParamUtil.getInt(req, "datPhongId");
        req.setAttribute("invoice", dao.findByDatPhongId(datPhongId));
        req.getRequestDispatcher("/views/hoadon/invoice.jsp").forward(req, resp);
    }
}
