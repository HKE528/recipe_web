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
confirmNotShardRecipe
function confirmShardRecipe(recipeId, selected){
    var resp = confirm("정말로 공유 하시겠습니까?");

    if(resp) {
        location.href="/recipe/my/shard/"+ recipeId + "?selected=" + selected + "&shard=" + true;
    }
}

function confirmNotShardRecipe(recipeId, selected){
    var resp = confirm("정말로 공유를 해제 하시겠습니까?");

    if(resp) {
        location.href="/recipe/my/shard/"+ recipeId + "?selected=" + selected + "&shard=" + false;
    }
}