package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.LoaiPhongDAO;
import com.nhom4.hotel.dao.PhongDAO;
import com.nhom4.hotel.entity.Phong;
import com.nhom4.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(urlPatterns = {"/rooms", "/room/detail", "/admin/rooms", "/admin/rooms/form", "/admin/rooms/save", "/admin/rooms/delete"})
public class RoomServlet extends HttpServlet {
    private final PhongDAO phongDAO = new PhongDAO();
    private final LoaiPhongDAO loaiPhongDAO = new LoaiPhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/room/detail".equals(path)) {
            Integer id = ParamUtil.getInt(req, "id");
            req.setAttribute("room", id == null ? null : phongDAO.findById(id));
            req.getRequestDispatcher("/views/rooms/detail.jsp").forward(req, resp);
            return;
        }
        if ("/admin/rooms".equals(path)) {
            req.setAttribute("rooms", phongDAO.search(ParamUtil.getString(req, "keyword"), ParamUtil.getInt(req, "loaiId"), ParamUtil.getString(req, "status")));
            req.setAttribute("types", loaiPhongDAO.findActive());
            req.getRequestDispatcher("/views/rooms/manager-list.jsp").forward(req, resp);
            return;
        }
        if ("/admin/rooms/form".equals(path)) {
            Integer id = ParamUtil.getInt(req, "id");
            req.setAttribute("room", id == null ? null : phongDAO.findById(id));
            req.setAttribute("types", loaiPhongDAO.findActive());
            req.getRequestDispatcher("/views/rooms/room-form.jsp").forward(req, resp);
            return;
        }
        if ("/admin/rooms/delete".equals(path)) {
            Integer id = ParamUtil.getInt(req, "id");
            try {
                if (id != null) phongDAO.delete(id);
                resp.sendRedirect(req.getContextPath() + "/admin/rooms");
            } catch (Exception e) {
                req.setAttribute("error", "Không thể xóa phòng đang có đặt phòng liên quan.");
                req.setAttribute("rooms", phongDAO.findAll());
                req.setAttribute("types", loaiPhongDAO.findActive());
                req.getRequestDispatcher("/views/rooms/manager-list.jsp").forward(req, resp);
            }
            return;
        }

        LocalDate ngayNhan = ParamUtil.getDate(req, "ngayNhan");
        LocalDate ngayTra = ParamUtil.getDate(req, "ngayTra");
        req.setAttribute("rooms", phongDAO.findAvailable(ngayNhan, ngayTra, ParamUtil.getInt(req, "loaiId")));
        req.setAttribute("types", loaiPhongDAO.findActive());
        req.getRequestDispatcher("/views/rooms/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Phong room = new Phong();
        Integer id = ParamUtil.getInt(req, "id");
        if (id != null) room.setId(id);
        room.setSoPhong(ParamUtil.getString(req, "soPhong"));
        room.setLoaiPhongId(ParamUtil.getInt(req, "loaiPhongId"));
        room.setGiaPhong(ParamUtil.getDecimal(req, "giaPhong"));
        room.setTrangThai(ParamUtil.getString(req, "trangThai"));
        room.setGhiChu(ParamUtil.getString(req, "ghiChu"));
        if (room.getId() > 0) phongDAO.update(room); else phongDAO.insert(room);
        resp.sendRedirect(req.getContextPath() + "/admin/rooms");
    }
}
