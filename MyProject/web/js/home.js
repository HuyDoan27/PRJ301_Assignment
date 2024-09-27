// Toggle hiển thị dropdown menu khi click vào icon đăng nhập
function toggleDropdown() {
    var dropdown = document.querySelector(".dropdown-menu");
    dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
}

// Ẩn dropdown khi click ra ngoài
window.onclick = function(event) {
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

