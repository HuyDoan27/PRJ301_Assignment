function toggleDropdown() {
    document.getElementById("myDropdown").classList.toggle("show");
}

/* Ẩn dropdown nếu click ra ngoài vùng dropdown */
window.onclick = function(event) {
    if (!event.target.matches('.login-icon') && !event.target.closest('.dropdown-menu')) {
        var dropdowns = document.getElementsByClassName("dropdown-menu");
        for (var i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
};

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