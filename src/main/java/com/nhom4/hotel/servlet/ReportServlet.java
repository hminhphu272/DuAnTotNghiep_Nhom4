package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.HoaDonDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/admin/reports")
public class ReportServlet extends HttpServlet {
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double tongDoanhThu = hoaDonDAO.getTongDoanhThu();
        int tongHoaDon = hoaDonDAO.getTongHoaDon();
        Map<String, Double> bieuDoDoanhThu = hoaDonDAO.getDoanhThuTheoThang();

        req.setAttribute("tongDoanhThu", tongDoanhThu);
        req.setAttribute("tongHoaDon", tongHoaDon);
        req.setAttribute("bieuDoDoanhThu", bieuDoDoanhThu);

        req.getRequestDispatcher("/report.jsp").forward(req, resp);
    }
}