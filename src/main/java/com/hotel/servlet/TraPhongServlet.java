package com.hotel.servlet;

import com.hotel.dao.DatPhongDAO;
import com.hotel.dao.HoaDonDAO;
import com.hotel.dao.PhongDAO;
import com.hotel.entity.DatPhong;
import com.hotel.entity.NguoiDung;
import com.hotel.entity.Phong;
import com.hotel.util.AuthUtil;
import com.hotel.util.DateUtil;
import com.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/staff/checkout")
public class TraPhongServlet extends HttpServlet {
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();
    private final PhongDAO phongDAO = new PhongDAO();
    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AuthUtil.isStaff(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.setAttribute("list", datPhongDAO.findByStatus("DA_NHAN_PHONG"));
        req.getRequestDispatcher("/views/letan/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!AuthUtil.isStaff(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        NguoiDung user = AuthUtil.getUser(req);
        int id = ParamUtil.getInt(req, "id");
        String phuongThuc = ParamUtil.getString(req, "phuongThuc");
        DatPhong dp = datPhongDAO.findById(id);
        if (dp != null && hoaDonDAO.findByDatPhongId(id) == null) {
            Phong p = phongDAO.findById(dp.getPhongId());
            long soNgay = DateUtil.days(dp.getNgayNhan(), dp.getNgayTra());
            BigDecimal tongTien = p.getGiaPhong().multiply(new BigDecimal(soNgay));
            hoaDonDAO.insert(id, user.getId(), tongTien, phuongThuc);
            datPhongDAO.updateStatus(id, "DA_TRA_PHONG");
            phongDAO.updateStatus(dp.getPhongId(), "TRONG");
        }
        resp.sendRedirect(req.getContextPath() + "/invoice/detail?datPhongId=" + id);
    }
}
