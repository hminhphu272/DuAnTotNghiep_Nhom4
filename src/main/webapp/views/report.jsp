<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Báo cáo thống kê</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body { background-color: #f4f6f9; }
        .stat-card { border: none; border-radius: 15px; transition: transform 0.2s; }
        .stat-card:hover { transform: translateY(-5px); }
    </style>
</head>
<body>
    <div class="container py-5">
        <h2 class="mb-4 text-dark"><i class="bi bi-bar-chart-fill"></i> Thống Kê & Báo Cáo Doanh Thu</h2>
        
        <div class="row mb-5">
            <div class="col-md-6 mb-3">
                <div class="card stat-card bg-primary text-white p-4 shadow">
                    <h5>TỔNG DOANH THU LŨY KẾ</h5>
                    <h2>
                        <c:out value="${String.format('%,.0f', tongDoanhThu)}"/> VND
                    </h2>
                    <p class="mb-0 text-white-50">Cập nhật thời gian thực</p>
                </div>
            </div>
            <div class="col-md-6 mb-3">
                <div class="card stat-card bg-success text-white p-4 shadow">
                    <h5>TỔNG SỐ ĐƠN ĐẶT PHÒNG</h5>
                    <h2>${tongHoaDon} Giao dịch</h2>
                    <p class="mb-0 text-white-50">Toàn bộ lịch sử đặt phòng</p>
                </div>
            </div>
        </div>

        <div class="card p-4 shadow-sm">
            <h4 class="mb-3 text-secondary">Doanh Thu Các Tháng (Năm nay)</h4>
            <div style="height: 400px; width: 100%;">
                <canvas id="doanhThuChart"></canvas>
            </div>
        </div>
    </div>

    <script>
        const ctx = document.getElementById('doanhThuChart').getContext('2d');
        
        // Đọc dữ liệu gửi về từ Servlet
        const labels = [];
        const dataValues = [];
        
        <c:forEach var="entry" items="${bieuDoDoanhThu}">
            labels.push("${entry.key}");
            dataValues.push(${entry.value});
        </c:forEach>

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Doanh thu (VND)',
                    data: dataValues,
                    backgroundColor: 'rgba(54, 162, 235, 0.7)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 2,
                    borderRadius: 5
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            callback: function(value) {
                                return value.toLocaleString('vi-VN') + ' đ';
                            }
                        }
                    }
                }
            }
        });
    </script>
</body>
</html>