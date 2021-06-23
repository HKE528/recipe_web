function confirmDelete(id){
    console.log("asdf");
    console.log(id);
    var resp = confirm("정말로 삭제할까요?");

    if(resp) {
        location.href="/recipe/my/delete/"+{id};
    }
}