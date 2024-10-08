
// Hiện dropdown khi click vào icon
document.addEventListener("DOMContentLoaded", function() {
    // Lấy phần tử dropdown
    const dropdown = document.getElementById("myDropdown");
    const loginIcon = document.querySelector(".login-icon");

    // Hiện dropdown khi click vào icon
    loginIcon.addEventListener("click", function(event) {
        event.stopPropagation(); // Ngăn không cho sự kiện lan truyền ra ngoài
        dropdown.classList.toggle("show");
    });

    // Ẩn dropdown khi click ra ngoài
    document.addEventListener("click", function(event) {
        if (!dropdown.contains(event.target) && !loginIcon.contains(event.target)) {
            if (dropdown.classList.contains("show")) {
                dropdown.classList.remove("show");
            }
        }
    });
});



function showInfo(info) {
    document.getElementById('productInfo').innerText = info; // Hiển thị thông tin sản phẩm
    document.getElementById('infoPopup').style.display = 'block'; // Hiện popup
}

function closeInfo() {
    document.getElementById('infoPopup').style.display = 'none'; // Ẩn popup
}

// Lấy tất cả các mục trên navbar
const navLinks = document.querySelectorAll('nav ul li a');

// Thêm sự kiện "click" cho mỗi mục
navLinks.forEach(link => {
    link.addEventListener('click', function () {
        // Xóa lớp "active" khỏi tất cả các mục
        navLinks.forEach(nav => nav.classList.remove('active'));

        // Thêm lớp "active" vào mục vừa được nhấn
        this.classList.add('active');
    });
});