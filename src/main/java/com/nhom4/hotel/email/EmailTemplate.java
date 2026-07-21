package com.nhom4.hotel.email;

import com.nhom4.hotel.entity.DatPhong;

public class EmailTemplate {

    public static String booking(DatPhong b){

        return """
<html>

<body style='font-family:Arial'>

<h2 style='color:#1976D2'>
ĐẶT PHÒNG THÀNH CÔNG
</h2>

<hr>

<p>Xin chào %s</p>

<p>Cảm ơn bạn đã đặt phòng.</p>

<table border='1' cellpadding='8'>

<tr>

<td>Phòng</td>

<td>%s</td>

</tr>

<tr>

<td>Loại</td>

<td>%s</td>

</tr>

<tr>

<td>Ngày nhận</td>

<td>%s</td>

</tr>

<tr>

<td>Ngày trả</td>

<td>%s</td>

</tr>

</table>

</body>

</html>

""".formatted(

b.getHoTenKhach(),

b.getSoPhong(),

b.getTenLoai(),

b.getNgayNhan(),

b.getNgayTra()

);

    }

}