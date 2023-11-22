$("#datePicker").datepicker({
  format: "yyyy-mm-dd",
  //   startDate: "-1d",
  endDate: "1d",
  autoclose: true,
  clearBtn: true,
  title: "Birth day",
  language: "ko",
  //   multidate: true,
});
const myModal = new bootstrap.Modal(document.getElementById('errorModal'))
$(document).ready(function() {

  $("#registrationForm").submit(function(event) {
    var userId = document.getElementById("userId").value;
    console.log(userId);
    if (!validateId(userId)) {
      document.getElementById("modalTitle").innerHTML = "아이디 지정 오류";
      document.getElementById("modalText").innerHTML= "ID는 3~20자 이어야 하며 영문자 또는 숫자만 포함해야 합니다.";
      myModal.show();
      event.preventDefault();
      return;
    }

    var password = document.getElementById("passwords").value;
    console.log(password);
    if (!validatePassword(password)) {
      document.getElementById("modalTitle").innerHTML = "비밀번호 지정 오류";
      document.getElementById("modalText").innerHTML= "비밀번호는 10~30자 이어야 하며 적어도 대문자, 소문자, 숫자, 특수문자(!@#$%^&)를 하나 이상 포함해야 합니다.";
      myModal.show();
      event.preventDefault();
      return;
    }

    var name = $("input[name='name']").val();
    if (!validateName(name)) {
      document.getElementById("modalTitle").innerHTML = "이름 지정 오류";
      document.getElementById("modalText").innerHTML= "한글 이름은 2~10자이어야 하며 영문 이름은 4~20자이어야 합니다.";
      myModal.show();
      event.preventDefault();
      return;
    }

    var phone = document.getElementById("phones").value;
    console.log(phone)
    if (!validatePhone(phone)) {
      document.getElementById("modalTitle").innerHTML = "전화번호 지정 오류";
      document.getElementById("modalText").innerHTML= "전화번호는 '-'를 포함한 12자리 또는 13자리이거나 '-'를 제외한 10자리 또는 11자리이어야 합니다.";
      myModal.show();
      event.preventDefault();
      return;
    }

    var address = $("input[name='address']").val();
    if (!validateAddress(address)) {
      document.getElementById("modalTitle").innerHTML = "주소 지정 오류";
      document.getElementById("modalText").innerHTML= "주소는 5~100자 이어야 합니다.";
      myModal.show();
      event.preventDefault();
      return;
    }

    var email = $("input[name='email']").val();
    if (!validateEmail(email)) {
      document.getElementById("modalTitle").innerHTML = "이메일 지정 오류";
      document.getElementById("modalText").innerHTML= "유효하지 않은 이메일 주소입니다. com 또는 org 또는 co.kr 또는 net 으로 끝나야합니다.";
      myModal.show();
      event.preventDefault();
      return;
    }

    var gitAddress = document.getElementById("git-address").value;
console.log(gitAddress);

if (!validateGithubAddress(gitAddress)) {
document.getElementById("modalTitle").innerHTML = "깃허브 지정 오류";
document.getElementById("modalText").innerHTML = "잘못된 입력입니다.";
myModal.show();
event.preventDefault();
return;
} else if (!gitAddress.startsWith('https://github.com/')) {
gitAddress = 'https://github.com/' + gitAddress;
document.getElementById("git-address").value = gitAddress;
}

console.log(gitAddress);

$.ajax({
type: "POST",
url: "/user/regist",
data: $("#registrationForm").serialize(),
success: function(response) {
  console.log(response);
},
error: function(error) {
  console.error(error);
}
});
});
});

function validateId(id) {
  return /^[a-zA-Z0-9]{3,20}$/.test(id);
}

function validatePassword(password) {
  return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&]).{10,30}$/.test(password);
}

function validateName(name) {
  return /^[\uAC00-\uD7A3]{2,10}$|^[\sa-zA-Z]{4,20}$/.test(name);
}

function validatePhone(phone) {
  return /^(?:\d{3}-\d{3}-\d{4}|\d{3}-\d{4}-\d{4}|\d{10}|\d{3}\d{4}\d{4})$/.test(phone);
}

function validateAddress(address) {
  return /^.{5,100}$/.test(address);
}

function validateEmail(email) {
  return /^[^\s@]+@[^\s@]+\.(co\.kr|com|org|net)$/.test(email);
}

function validateGithubAddress(gitAddress) {
return /^(https:\/\/github.com\/)?[a-zA-Z0-9]+$/.test(gitAddress);
}