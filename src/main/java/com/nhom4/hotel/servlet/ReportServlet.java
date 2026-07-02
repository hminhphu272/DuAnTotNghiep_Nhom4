package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.DatPhongDAO;
import com.nhom4.hotel.dao.HoaDonDAO;
import com.nhom4.hotel.dao.StatisticDAO;
import com.nhom4.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/admin/reports")
public class ReportServlet extends HttpServlet {
    private final StatisticDAO statisticDAO = new StatisticDAO();
    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDate from = ParamUtil.getDate(req, "from");
        LocalDate to = ParamUtil.getDate(req, "to");
        req.setAttribute("revenue", hoaDonDAO.revenue(from, to));
        req.setAttribute("roomStats", statisticDAO.roomStatus());
        req.setAttribute("bookingStats", statisticDAO.bookingStatus());
        req.setAttribute("bookings", datPhongDAO.findAll("", ""));
        req.getRequestDispatcher("/views/reports/dashboard.jsp").forward(req, resp);
    }
}
