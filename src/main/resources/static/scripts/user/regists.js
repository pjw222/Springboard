  $("#datePicker").datepicker({
    format: "yyyy-mm-dd",
    //   startDate: "-1d",
    endDate: "1d",
    autoclose: true,
    clearBtn: true,
    title: "Birth day",
    language: "ko"
    //   multidate: true,
  });
// console.log("입력하고 싶은 English input".replace(/[a-z]+/,""));

const idReg = /^[a-z0-9]{3,20}$/i;
const pwReg = /^(?=.*[a-zAz])(?=.*[!@#$%^&])(?=.*[0-9]).{10,30}$/;
const koreanReg = /[ㄱ-ㅎㅏ-ㅣ가-힣]/g;
const phoneReg = /^[0-9]{3}-[0-9]{3,4}-[0-9]{4}$/;
const emailReg = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+(.com|.net|.co.kr|.org)$/i;
// const myModal = new bootstrap.Modal(document.getElementById('errorModal'))

document.getElementById("registrationForm").onsubmit = function(e){

    
    // id >> 3보다크고 20보다작아야함 // << 한글이 있는지 어떻게 찾을까?
    // if (e.target.userId.value.length < 3 || e.target.userId.value.length > 20){
    //     e.preventDefault();
    //     console.log("아이디 길이가 안맞아");
    // }
    const tempName = e.target.name.value.replace(koreanReg,"aa");
    const tempPhone = e.target.phone.value.replace(/^(\d{3})(\d{3,4})(\d{4})$/,`$1-$2-$3`);
    
    let msg = "";
    console.log(tempPhone);
    if(!idReg.test(e.target.userId.value)){
        if(e.target.userId.value.length<3 || e.target.userId.value.length >20){
            msg ="아이디의 길이는 3~20으로 해주세요.";
                // document.getElementById("modalTitle").innerHTML = "아이디 길이 지정 오류";
                // document.getElementById("modalText").innerHTML= msg;
                // myModal.show();
           
        }
        else{
            msg = "아이디는 영어와 숫자만 포함할 수 있어요.";
            // document.getElementById("modalTitle").innerHTML = "아이디 한글 입력 오류";
            // document.getElementById("modalText").innerHTML= msg;
            // myModal.show();
        }
    }else if (!pwReg.test(e.target.password.value)){
        if(e.target.password.value.length < 10 || e.target.password.value.length > 30){
            msg = "비밀번호의 길이는 10~30으로 해주세요."
            // document.getElementById("modalTitle").innerHTML = "비밀번호 길이 지정 오류";
            // document.getElementById("modalText").innerHTML= msg;
            // myModal.show();
        }
        else{
            msg = "비밀번호는 대소문자, 숫자, 특수문자 (!@#$%^&)를 포함해야해요."
            // document.getElementById("modalTitle").innerHTML = "비밀번호 조건 입력 오류";
            // document.getElementById("modalText").innerHTML= msg;
            // myModal.show();
        }
    }else if (!koreanReg.test(e.target.name.value)){
        if(e.target.name.value.length > 10 ){
            msg = "이름의 길이는 10글자이하로 해주세요."
            // document.getElementById("modalTitle").innerHTML = "이름 길이 지정 오류";
            // document.getElementById("modalText").innerHTML= msg;
            // myModal.show();
        }
        else if(e.target.name.value.length == 0){
            msg = "이름 입력안햇어요"
            // document.getElementById("modalTitle").innerHTML = "이름 미입력 오류";
            // document.getElementById("modalText").innerHTML= msg;
            // myModal.show();
        }
        else{
            msg = "한글만 입력하세요"
            // document.getElementById("modalTitle").innerHTML = "영어 입력시 오류";
            // document.getElementById("modalText").innerHTML= msg;
            // myModal.show();
        }
    }else if (!phoneReg.test(tempPhone)){
            if(tempPhone.length >11){               
                msg = "전화번호의 길이는 11~13으로 해주세요."
                // document.getElementById("modalTitle").innerHTML = "전화번호 길이 지정 오류";
                // document.getElementById("modalText").innerHTML= msg;
                // myModal.show();       
            }
            else if(tempPhone.length == 0){
                msg = "전화번호를 입력해주세요."
                // document.getElementById("modalTitle").innerHTML = "전화번호 미지정 오류";
                // document.getElementById("modalText").innerHTML= msg;
                // myModal.show();  
            }
            else{
                msg = "잘못된 전화번호를 입력헀습니다"
                // document.getElementById("modalTitle").innerHTML = "전화번호 지정 오류";
                // document.getElementById("modalText").innerHTML= msg;
                // myModal.show(); 
            }
    }else if(e.target.address.value.length > 0 && (e.target.address.value.length < 5 || e.target.address.value.length >100)){
        msg = "주소를 잘못된 형식 또는 잘못된 주소를 입력헀습니다"
                // document.getElementById("modalTitle").innerHTML = "주소 지정 오류";
                // document.getElementById("modalText").innerHTML= msg;
                // myModal.show();
    }
    else if (!emailReg.test(e.target.email.value)){ 
            msg = "유효하지 않은 이메일 주소입니다. com 또는 org 또는 co.kr 또는 net 으로 끝나야합니다."
            // document.getElementById("modalTitle").innerHTML = "이메일 조건 입력 오류";
            // document.getElementById("modalText").innerHTML= msg;
            // myModal.show();
    }
 console.log(e.target["git-address"].value);
    if(msg){
    e.preventDefault();
    document.getElementById("modal-container").classList.add("on");
    document.getElementById("modal-msg").innerHTML = msg;
    }else{
        e.target.phone.value = tempPhone;
        if(e.target["git-address"].value.length > 0 && !e.target["git-address"].value.startsWith("https://github.com/")){
            e.target["git-address"].value =
            "https://github.com/" + e.target["git-address"].value;
        }
    }
    console.log(e.target["git-address"].value);
    
    // if (!idReg.test(e.target.userId.value)){
    //     e.preventDefault();
    //     console.log("아이디 지정이 잘못됬어");
    // }
    // // if (e.target.userId.value.replace(/[가-힣]/g,"")){ //<< 정규표현식

    // // }
    // if (e.target.password.value.length < 10 || e.target.password.value.length > 30){
    //     e.preventDefault();
    //     console.log("비밀번호 길이가 안맞아");
    // }
}
// document.getElementById("parent").onwheel = (e) => {
//     e.stopPropagation();
// };
// document.getElementById("parent").onclick = (e) => {
//     e.stopPropagation();
//     console.log("부모 클릭");
// };
// document.getElementById("child1").onclick = (e) => {
//     console.log("child1클릭");
// };
// document.getElementById("child-child").onclick = (e) => {
//     e.stopPropagation();
//     console.log("child-child 클릭");
// };
// document.getElementById("child2").onclick = (e) => { 
//     console.log("child2클릭");
// };
