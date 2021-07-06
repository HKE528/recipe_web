function confirmDeleteRecipe(recipeId){
    var resp = confirm("정말로 삭제할까요?");

    if(resp) {
        location.href="/recipe/my/delete/"+ recipeId;
    }
}

function confirmDeleteMember(){
    var resp = confirm("정말로 탈퇴 하시겠습니까?");

    if(resp) {
        location.href="/member/drop";
    }
}