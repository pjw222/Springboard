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

// comment.js
const commentListElem = document.getElementById("comment-list");

const showReplyForm = (parentCommentId) => {
    document.getElementById("replyForm").style.display = "block";
    document.getElementById("parentCommentId").value = parentCommentId;
};

const addReply = async () => {
    document.getElementById("replyForm").style.display = "none";
};

const createCommentElement = (comment) => {
    const liElem = document.createElement("li");
    liElem.innerHTML = `${comment.content} - ${comment.userName} - ${comment.createdAt}`;


    const replyButton = document.createElement("button");
    replyButton.setAttribute("type", "button");
    replyButton.textContent = "대댓글";
    replyButton.onclick = () => showReplyForm(comment.id);

    liElem.append(replyButton);

    if (comment.children && comment.children.length > 0) {
        const olElem = document.createElement("ol");
        comment.children.forEach((child) => {
            const childLiElem = createCommentElement(child);
            olElem.append(childLiElem);
        });
        liElem.append(olElem);
    }

    return liElem;
};

const getList = async () => {
    const list = (await axios.get(`/comments?boardId=${commentListElem.dataset.boardId}`)).data;
    console.log(list);

    list.forEach((item) => {
        const commentElem = createCommentElement(item);
        commentListElem.append(commentElem);
    });
};

getList();



