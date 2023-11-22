document.getElementById("addBoard").onsubmit = function(e){
    let msg ="";
    if(e.target.title.value.length == 0){
        msg = "제목이 비어있어요 입력해주세요.";
    }
    else if(e.target.content.value.length == 0){
        msg = "내용이 비어있어요 입력해주세요.";
    }
    
   
   if(msg){
       e.preventDefault();
       document.getElementById("modal-container").classList.add("on");
       document.getElementById("modal-msg").innerHTML = msg;
   }
   }   