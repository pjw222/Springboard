
const idsReg = /^[a-z0-9]{3,20}$/i;
const pwsReg = /^(?=.*[a-zAz])(?=.*[!@#$%^&])(?=.*[0-9]).{10,30}$/;

document.getElementById("loginbox").onsubmit = function(e){
 let msg ="";   
 if(!idsReg.test(e.target.userId.value)){
    if( e.target.userId.value.length == 0){
        msg ="아이디가 비어있어요 입력해주세요.";      
    }else if(e.target.userId.value.length < 3 || e.target.userId.value.length >20){
        msg="아이디의 길이는 3~20으로 해주세요.";
    }
    else{
        msg = "아이디는 영어와 숫자만 포함할 수 있어요.";
    }
}
else if (!pwsReg.test(e.target.password.value)){
        if(e.target.password.value.length == 0){            
            msg = "비밀번호가 비어있어요 입력해주세요.";
        }
        else if(e.target.password.value.length < 10 || e.target.password.value.length > 30){
            msg = "비밀번호의 길이는 10~30으로 해주세요."
        }
        else{
            msg = "비밀번호는 대소문자, 숫자, 특수문자 (!@#$%^&)를 포함해야해요."
        }
}
if(msg){
    e.preventDefault();
    document.getElementById("modal-container").classList.add("on");
    document.getElementById("modal-msg").innerHTML = msg;
}
}   