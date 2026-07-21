package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.DatPhongDAO;
import com.nhom4.hotel.dao.HoaDonDAO;
import com.nhom4.hotel.email.EmailService;
import com.nhom4.hotel.entity.DatPhong;
import com.nhom4.hotel.entity.HoaDon;
import com.nhom4.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/invoices")
public class PaymentServlet extends HttpServlet {

    private final HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String keyword = ParamUtil.getString(req, "keyword");
        String status = ParamUtil.getString(req, "status");

        req.setAttribute("invoices", hoaDonDAO.search(keyword, status));
        req.getRequestDispatcher("/views/frontdesk/invoices.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Integer id = ParamUtil.getInt(req, "id");
        String phuongThuc = ParamUtil.getString(req, "phuongThuc");

        HoaDon hoaDon = (id == null) ? null : hoaDonDAO.findById(id);

        if (hoaDon == null || "Đã thanh toán".equals(hoaDon.getTrangThai())) {
            resp.sendRedirect(req.getContextPath() + "/invoices?error=1");
            return;
        }

        // Cập nhật trạng thái thanh toán
        hoaDonDAO.markPaid(
                hoaDon.getId(),
                (phuongThuc == null || phuongThuc.isBlank()) ? "Tiền mặt" : phuongThuc
        );

        // Cập nhật trạng thái đặt phòng
        datPhongDAO.updateStatus(hoaDon.getDatPhongId(), "Đã thanh toán");

        // Lấy thông tin đặt phòng để gửi email
        DatPhong booking = datPhongDAO.findById(hoaDon.getDatPhongId());

        if (booking != null
                && booking.getEmailKhach() != null
                && !booking.getEmailKhach().isBlank()) {

            try {

                String html = """
                    <html>
                    <body style="font-family:Arial">

                        <h2>Thanh toán thành công</h2>

                        <p>Xin chào <b>%s</b>,</p>

                        <p>Khách sạn đã xác nhận thanh toán của bạn.</p>

                        <table border="1" cellpadding="8" cellspacing="0">
                            <tr>
                                <td><b>Khách hàng</b></td>
                                <td>%s</td>
                            </tr>
                            <tr>
                                <td><b>Phòng</b></td>
                                <td>%s</td>
                            </tr>
                            <tr>
                                <td><b>Ngày nhận</b></td>
                                <td>%s</td>
                            </tr>
                            <tr>
                                <td><b>Ngày trả</b></td>
                                <td>%s</td>
                            </tr>
                            <tr>
                                <td><b>Phương thức</b></td>
                                <td>%s</td>
                            </tr>
                        </table>

                        <br>

                        <p>Cảm ơn bạn đã sử dụng dịch vụ của khách sạn.</p>

                    </body>
                    </html>
                    """.formatted(
                        booking.getHoTenKhach(),
                        booking.getHoTenKhach(),
                        booking.getSoPhong(),
                        booking.getNgayNhan(),
                        booking.getNgayTra(),
                        (phuongThuc == null || phuongThuc.isBlank()) ? "Tiền mặt" : phuongThuc
                );

                EmailService.send(
                        booking.getEmailKhach(),
                        "Thanh toán thành công",
                        html
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        resp.sendRedirect(req.getContextPath() + "/invoices?success=1");
    }
}