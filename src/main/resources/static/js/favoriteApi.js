function addFavorite(id) {
//put /favorite/add/{id}
    $.ajax({
        url: '/favorite/add/' + id,
        type: 'PUT',
        success: function() {
            location.reload();
        },
        error: function() {
            location.href = "/member/login";
        }
    });
}

function deleteFavorite(id) {
//Delete /favorite/delete/{id}
    $.ajax({
        url: '/favorite/delete/' + id,
        type: 'DELETE',
        success: function() {
            location.reload();
        },
        error: function() {
            location.href = "/member/login";
        }
    });
}