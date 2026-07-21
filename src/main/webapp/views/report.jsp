<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Báo Cáo Thống Kê - Pleiades Hotel</title>
    
    <!-- Nhúng CSS phong cách chuẩn của nhóm -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    
    <!-- Chart.js hỗ trợ vẽ biểu đồ -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <style>
        /* Tùy chỉnh nâng cao giao diện Thống Kê dựa trên biến CSS gốc */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin-bottom: 28px;
        }

        .stat-card-luxury {
            background: linear-gradient(135deg, var(--navy) 0%, var(--navy-2) 100%);
            color: #ffffff;
            border-radius: 22px;
            padding: 24px;
            box-shadow: 0 16px 36px rgba(15, 23, 47, 0.12);
            border: 1px solid rgba(255, 255, 255, 0.08);
            position: relative;
            overflow: hidden;
            transition: transform 0.25s ease, box-shadow 0.25s ease;
        }

        .stat-card-luxury:hover {
            transform: translateY(-4px);
            box-shadow: 0 20px 40px rgba(15, 23, 47, 0.18);
        }

        .stat-card-luxury.teal-variant {
            background: linear-gradient(135deg, #0f766e 0%, var(--teal-dark) 100%);
        }

        .stat-card-luxury.amber-variant {
            background: linear-gradient(135deg, #78350f 0%, #b45309 100%);
        }

        .stat-card-luxury .card-label {
            font-size: 13px;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 1px;
            opacity: 0.8;
            margin-bottom: 8px;
            display: block;
        }

        .stat-card-luxury .card-value {
            font-size: 32px;
            font-weight: 800;
            line-height: 1.1;
            margin-bottom: 12px;
            color: #ffffff;
        }

        .stat-card-luxury .card-value small {
            font-size: 16px;
            font-weight: 600;
            opacity: 0.85;
        }

        .stat-card-luxury .card-footer-info {
            padding-top: 12px;
            border-top: 1px solid rgba(255, 255, 255, 0.12);
            display: flex;
            align-items: center;
            justify-content: space-between;
            font-size: 12px;
            opacity: 0.85;
        }

        .chart-panel {
            background: #ffffff;
            border: 1px solid var(--line);
            border-radius: 24px;
            padding: 24px;
            box-shadow: 0 12px 34px rgba(15, 23, 47, 0.06);
        }

        .chart-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 14px;
            border-bottom: 1px solid var(--line);
        }

        .chart-header h3 {
            margin: 0;
            font-size: 20px;
            color: var(--navy);
            font-weight: 800;
        }

        @media (max-width: 850px) {
            .stats-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>

    <!-- Header chung của dự án -->
    <jsp:include page="include/header.jsp" />

    <div class="container">
        
        <!-- Section Title chuẩn CSS nhóm -->
        <div class="section-title">
            <div>
                <h2>Thống kê & Báo cáo</h2>
                <p class="muted" style="margin: 6px 0 0 0;">Phân tích chỉ số kinh doanh và tổng doanh thu toàn hệ thống</p>
            </div>
            <div>
                <button onclick="window.print()" class="btn secondary small">
                    🖨️ In báo cáo
                </button>
            </div>
        </div>

        <!-- Khối 3 Card Thống Kê Sang Trọng -->
        <div class="stats-grid">
            
            <!-- Card 1: Tổng Doanh Thu -->
            <div class="stat-card-luxury">
                <span class="card-label">Tổng Doanh Thu Lũy Kế</span>
                <div class="card-value">
                    <c:out value="${String.format('%,.0f', tongDoanhThu)}"/>
                    <small>VND</small>
                </div>
                <div class="card-footer-info">
                    <span>⚡ Cập nhật thời gian thực</span>
                    <span>Toàn thời gian</span>
                </div>
            </div>

            <!-- Card 2: Số Đơn Đặt Phòng -->
            <div class="stat-card-luxury teal-variant">
                <span class="card-label">Tổng Đơn Đặt Phòng</span>
                <div class="card-value">
                    ${tongHoaDon}
                    <small>Giao dịch</small>
                </div>
                <div class="card-footer-info">
                    <span>Lịch sử đặt phòng</span>
                    <span>Đã hoàn tất</span>
                </div>
            </div>

            <!-- Card 3: Giá Trị Trung Bình/Đơn -->
            <div class="stat-card-luxury amber-variant">
                <span class="card-label">Trung Bình / Giao Dịch</span>
                <div class="card-value">
                    <c:choose>
                        <c:when test="${tongHoaDon > 0}">
                            <c:out value="${String.format('%,.0f', tongDoanhThu / tongHoaDon)}"/>
                        </c:when>
                        <c:otherwise>0</c:otherwise>
                    </c:choose>
                    <small>VND</small>
                </div>
                <div class="card-footer-info">
                    <span>Hiệu suất đơn</span>
                    <span>Giá trị TB</span>
                </div>
            </div>

        </div>

        <!-- Khung Biểu Đồ Doanh Thu -->
        <div class="chart-panel">
            <div class="chart-header">
                <div>
                    <h3>Biểu Đồ Doanh Thu Các Tháng (Năm Nay)</h3>
                    <span class="muted" style="font-size: 13px;">Ghi nhận các khoản thanh toán thực tế theo từng tháng</span>
                </div>
                <span class="status" style="background: #eff6ff; color: #1d4ed8;">
                    Năm 2026
                </span>
            </div>

            <div style="height: 400px; width: 100%;">
                <canvas id="doanhThuChart"></canvas>
            </div>
        </div>

    </div>

    <!-- Script Vẽ Biểu Đồ Với Tông Màu Teal/Navy Chuẩn CSS Nhóm -->
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const ctx = document.getElementById('doanhThuChart').getContext('2d');
            
            // Đọc dữ liệu gửi về từ Servlet
            const labels = [];
            const dataValues = [];
            
            <c:forEach var="entry" items="${bieuDoDoanhThu}">
                labels.push("${entry.key}");
                dataValues.push(${entry.value});
            </c:forEach>

            // Đổ Gradient xanh mờ đúng tông var(--navy) & var(--teal)
            const gradient = ctx.createLinearGradient(0, 0, 0, 380);
            gradient.addColorStop(0, '#132042');   /* var(--navy-2) */
            gradient.addColorStop(1, '#29d3c5');   /* var(--teal) */

            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Doanh thu (VND)',
                        data: dataValues,
                        backgroundColor: gradient,
                        borderColor: '#0f172f',
                        borderWidth: 1,
                        borderRadius: 8,
                        barThickness: 42
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            display: false
                        },
                        tooltip: {
                            backgroundColor: '#0f172f',
                            titleFont: { family: "'Segoe UI', sans-serif", size: 14, weight: 'bold' },
                            bodyFont: { family: "'Segoe UI', sans-serif", size: 13 },
                            padding: 12,
                            cornerRadius: 10,
                            displayColors: false,
                            callbacks: {
                                label: function(context) {
                                    let value = context.raw || 0;
                                    return ' Doanh thu: ' + value.toLocaleString('vi-VN') + ' VNĐ';
                                }
                            }
                        }
                    },
                    scales: {
                        x: {
                            grid: {
                                display: false
                            },
                            ticks: {
                                font: { family: "'Segoe UI', sans-serif", weight: '600' },
                                color: '#667085'
                            }
                        },
                        y: {
                            beginAtZero: true,
                            grid: {
                                color: '#eef2f7'
                            },
                            ticks: {
                                font: { family: "'Segoe UI', sans-serif" },
                                color: '#667085',
                                callback: function(value) {
                                    if (value >= 1000000) {
                                        return (value / 1000000) + ' Tr';
                                    }
                                    return value.toLocaleString('vi-VN') + ' đ';
                                }
                            }
                        }
                    }
                }
            });
        });
    </script>

    <!-- Footer chung của nhóm -->
    <jsp:include page="include/footer.jsp" />

</body>
</html>