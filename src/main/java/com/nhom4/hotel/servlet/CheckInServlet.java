package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.DatPhongDAO;
import com.nhom4.hotel.dao.PhongDAO;
import com.nhom4.hotel.email.EmailService;
import com.nhom4.hotel.entity.DatPhong;
import com.nhom4.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet dành cho lễ tân: nhận phòng cho khách khi đơn đặt phòng đã được xác nhận.
 * - GET  /checkin : danh sách đơn "Đã xác nhận" đang chờ khách đến nhận phòng.
 * - POST /checkin : xác nhận nhận phòng cho 1 đơn -> đổi trạng thái đơn + trạng thái phòng.
 */
@WebServlet("/checkin")
public class CheckInServlet extends HttpServlet {
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();
    private final PhongDAO phongDAO = new PhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = ParamUtil.getString(req, "keyword");
        req.setAttribute("bookings", datPhongDAO.search(keyword, "Đã xác nhận"));
        req.getRequestDispatcher("/views/frontdesk/checkin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = ParamUtil.getInt(req, "id");
        DatPhong booking = id == null ? null : datPhongDAO.findById(id);

        if (booking == null || !"Đã xác nhận".equals(booking.getTrangThai())) {
            resp.sendRedirect(req.getContextPath() + "/checkin?error=1");
            return;
        }

        datPhongDAO.updateStatus(booking.getId(), "Đã nhận phòng");

     // lấy lại dữ liệu sau khi cập nhật (nếu cần)
     booking = datPhongDAO.findById(id);

     try {

    	    if (booking.getEmailKhach() != null && !booking.getEmailKhach().isBlank()) {

    	        EmailService.send(
    	            booking.getEmailKhach(),
    	            "Xác nhận nhận phòng",
    	            """
    	            <html>
    	            <body style="font-family: Arial">
    	                <h2>Nhận phòng thành công</h2>

    	                <p>Xin chào <b>%s</b>,</p>

    	                <p>Bạn đã hoàn tất thủ tục nhận phòng.</p>

    	                <p>Chúc bạn có kỳ nghỉ vui vẻ!</p>
    	            </body>
    	            </html>
    	            """.formatted(booking.getHoTenKhach())
    	        );

    	    }

    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
        phongDAO.updateStatus(booking.getPhongId(), "Đang sử dụng");
        resp.sendRedirect(req.getContextPath() + "/checkin?success=1");
    }
}