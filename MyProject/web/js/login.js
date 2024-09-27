// Chuyển đổi giữa form Đăng nhập và Đăng ký
function toggleForm(isRegistering) {
    let formTitle = document.getElementById('form-title');
    let submitBtn = document.getElementById('submit-btn');
    let confirmPasswordGroup = document.getElementById('confirm-password-group');
    let toggleFormText = document.getElementById('toggle-form');

    if (isRegistering) {
        formTitle.innerText = 'Đăng Ký';
        submitBtn.innerText = 'Đăng Ký';
        confirmPasswordGroup.style.display = 'block';
        toggleFormText.innerHTML = 'Đã có tài khoản? <a href="#" id="switch-to-login">Đăng nhập</a>';
        document.getElementById('switch-to-login').addEventListener('click', function(event) {
            event.preventDefault();
            toggleForm(false);
        });
    } else {
        formTitle.innerText = 'Đăng Nhập';
        submitBtn.innerText = 'Đăng Nhập';
        confirmPasswordGroup.style.display = 'none';
        toggleFormText.innerHTML = 'Chưa có tài khoản? <a href="#" id="switch-to-register">Đăng ký</a>';
        document.getElementById('switch-to-register').addEventListener('click', function(event) {
            event.preventDefault();
            toggleForm(true);
        });
    }
}

// Mặc định hiển thị form Đăng nhập
document.getElementById('switch-to-register').addEventListener('click', function(event) {
    event.preventDefault();
    toggleForm(true);
});
