package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.LoaiPhongDAO;
import com.nhom4.hotel.entity.LoaiPhong;
import com.nhom4.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/types", "/admin/types/save", "/admin/types/delete"})
public class LoaiPhongServlet extends HttpServlet {
    private final LoaiPhongDAO dao = new LoaiPhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/admin/types/delete".equals(path)) {
            Integer id = ParamUtil.getInt(req, "id");
            if (id != null) dao.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/types");
            return;
        }
        Integer editId = ParamUtil.getInt(req, "editId");
        req.setAttribute("edit", editId == null ? null : dao.findById(editId));
        req.setAttribute("types", dao.findAll());
        req.getRequestDispatcher("/views/types/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LoaiPhong type = new LoaiPhong();
        Integer id = ParamUtil.getInt(req, "id");
        if (id != null) type.setId(id);
        type.setTenLoai(ParamUtil.getString(req, "tenLoai"));
        type.setMoTa(ParamUtil.getString(req, "moTa"));
        Integer sucChua = ParamUtil.getInt(req, "sucChua");
        type.setSucChua(sucChua == null ? 1 : sucChua);
        type.setGiaCoBan(ParamUtil.getDecimal(req, "giaCoBan"));
        type.setTrangThai("1".equals(ParamUtil.getString(req, "trangThai")));
        if (type.getId() > 0) dao.update(type); else dao.insert(type);
        resp.sendRedirect(req.getContextPath() + "/admin/types");
    }
}
