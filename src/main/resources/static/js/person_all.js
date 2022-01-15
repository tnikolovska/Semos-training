$(document).ready(function (){
    $('.table #deleteBtn').on('click',function (e){
        e.preventDefault();
        var href = $(this).attr('href');
        $('#deleteModal #delRef').attr('href',href);
        $('#deleteModal').modal();
    });
});
