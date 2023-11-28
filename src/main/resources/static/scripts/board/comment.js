// const commentListElem = document.getElementById("comment-list");

// const getList = async () => {
//   const list = (
//     await axios.get(`/comments?boardId=${commentListElem.dataset.boardId}`)
//   ).data;
//   console.log(list);

//   list.forEach((item) => {
//     const tempLiElem = document.createElement("li");
//     tempLiElem.innerHTML = `${item.content} - ${item.userId} -${item.createdAt}`;
//     const tempOlElem = document.createElement("ol");
//     item.children.forEach((child) => {
//       const tempLiElem2 = document.createElement("li");
//       tempLiElem2.innerHTML = `${child.content} - ${child.userId} - ${child.createdAt}`;
//       tempOlElem.append(tempLiElem2);
//       item.children.forEach((child2) => {
//         const tempLiElem3 = document.createElement("li");
//         tempLiElem3.innerHTML = `${child2.content} - ${child2.userId} - ${child2.createdAt}`;
//         tempOlElem.append(tempLiElem3);
//         item.children.forEach((child3) => {
//             const tempLiElem4 = document.createElement("li");
//             tempLiElem4.innerHTML = `${child3.content} - ${child3.userId} - ${child3.createdAt}`;
//             tempOlElem.append(tempLiElem4);  
//           });  
//       });  
//     });
//     tempLiElem.append(tempOlElem);

//     commentListElem.append(tempLiElem);
//   });
// };

// getList();

//  
// const commentListElem = document.getElementById("comment-list");


// const showReplyForm = (parentCommentId) => {
//     document.getElementById("replyForm").style.display = "block";
//     document.getElementById("parentCommentId").value = parentCommentId;
// };

// const addReply = async () => {
//     document.getElementById("replyForm").style.display = "none";
// };

// const createCommentElement = (comment) => {
//     const liElem = document.createElement("li");
//     liElem.innerHTML = `${comment.content} - ${comment.userName} - ${new Date(comment.createdAt)}`;


//     const replyButton = document.createElement("button");
//     replyButton.setAttribute("type", "button");
//     replyButton.textContent = "대댓글";
//     replyButton.onclick = () => showReplyForm(comment.id);

//     liElem.append(replyButton);

//     if (comment.children && comment.children.length > 0) {
//         const olElem = document.createElement("ol");
//         comment.children.forEach((child) => {
//             const childLiElem = createCommentElement(child);
//             olElem.append(childLiElem);
//         });
//         liElem.append(olElem);
//     }

//     return liElem;
// };

// const getList = async () => {
//     const list = (await axios.get(`/comments?boardId=${commentListElem.dataset.boardId}`)).data;
//     console.log(list);

//     list.forEach((item) => {
//         const commentElem = createCommentElement(item);
//         commentListElem.append(commentElem);
//     });
// };

// getList();




const commentListElem = document.getElementById("comment-list");
const commentForm = document.getElementById("board-comment");

const getList = async () => {
  const data = (
    await axios.get(`/comments?boardId=${commentListElem.dataset.boardId}&start=${commentListElem.children.length}`)
  ).data;
  if(data.end){
    document.getElementById("add-comment-btn").outerHTML = "";
  }
  console.log(data);
  setList(data.list, commentListElem);
};
function setList(data, parentElem) {
    data.forEach((item, idx) => {
      const tempLiElem = document.createElement("div");
      // if (!idx) {
      const tempArrowElem = document.createElement("img");
      tempArrowElem.classList.add("recomment-img");
      tempArrowElem.src = "/imgs/right-down.png";
      tempLiElem.append(tempArrowElem);
      // }
      tempLiElem.classList.add("comment-item");
      tempLiElem.innerHTML += `댓글: ${item.content} 글쓴이: <span class="comment-user-name">${
        item.userName
      }</span> / 작성일자: ${new Date(item.createdAt)}`;
  
      if (commentForm) {
        const tempButtonOnElem = document.createElement("button");
        tempButtonOnElem.innerHTML = "댓글";
        tempButtonOnElem.classList.add("btn", "btn-primary");
        tempLiElem.append(tempButtonOnElem);
  
        const tempFormElem = document.createElement("form");
        tempFormElem.action = "/comments/add";
        tempFormElem.method = "post";
        tempLiElem.append(tempFormElem);
  
        const tempUserIdElem = document.createElement("input");
        tempUserIdElem.type = "hidden";
        tempUserIdElem.name = "user_id";
        tempUserIdElem.value = commentForm["user_id"].value;
        tempFormElem.append(tempUserIdElem);
  
        const tempBoardIdElem = document.createElement("input");
        tempBoardIdElem.type = "hidden";
        tempBoardIdElem.name = "board_id";
        tempBoardIdElem.value = item.boardId;
        tempFormElem.append(tempBoardIdElem);
  
        const tempCommentIdElem = document.createElement("input");
        tempCommentIdElem.type = "hidden";
        tempCommentIdElem.name = "comment_id";
        tempCommentIdElem.value = item.id;
        tempFormElem.append(tempCommentIdElem);
  
        const tempContentElem = document.createElement("input");
        tempContentElem.name = "content";
        tempFormElem.append(tempContentElem);
  
        tempButtonOnElem.onclick = () => {
          tempFormElem.classList.toggle("on");
          tempButtonOnElem.classList.toggle("btn-primary");
          tempButtonOnElem.classList.toggle("btn-dark");
          if (tempButtonOnElem.className.indexOf("btn-primary") > -1) {
            tempButtonOnElem.innerHTML = "댓글";
          } else {
            tempButtonOnElem.innerHTML = "취소";
            tempContentElem.focus();
          }
        };
  
        const tempButtonElem = document.createElement("button");
        tempButtonElem.classList.add("btn", "btn-primary");
        tempButtonElem.innerHTML = "댓글 추가";
        tempFormElem.append(tempButtonElem);
      }
  
      const tempOlElem = document.createElement("div");
      tempOlElem.classList.add("comment-list");
      setList(item.children, tempOlElem);
      tempLiElem.append(tempOlElem);
  
      parentElem.append(tempLiElem);
    });
  }
  
  getList();

  document.getElementById("add-comment-btn").onclick= () =>{
    getList();
  };

