// Toggle hiển thị dropdown menu khi click vào icon đăng nhập
function toggleDropdown() {
    // Điều hướng về trang login.html khi nhấn vào icon
    window.location.href = "../login.html";
}

function showDropdown() {
    var dropdown = document.querySelector(".dropdown-menu");
    dropdown.style.display = "block";
}

// Ẩn dropdown khi chuột rời khỏi
function hideDropdown() {
    var dropdown = document.querySelector(".dropdown-menu");
    dropdown.style.display = "none";
}


// Ẩn dropdown khi click ra ngoài
window.onclick = function (event) {
    if (!event.target.matches('.login-icon')) {
        var dropdown = document.querySelector(".dropdown-menu");
        if (dropdown.style.display === "block") {
            dropdown.style.display = "none";
        }
    }
}

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