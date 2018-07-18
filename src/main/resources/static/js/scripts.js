$(document).ready(function(){

    $("#tableUsers").on('click','#editModalShow',function(){

        console.log("button pressed");
        var currentRow=$(this).closest("tr");

        var colId=currentRow.find("td:eq(0)").text();
        var colLogin=currentRow.find("td:eq(1)").text();
        var colPass=currentRow.find("td:eq(2)").text();
        var colEmail=currentRow.find("td:eq(3)").text();

        $("#modal_id").val(colId);
        $("#modal_login").val(colLogin);
        $("#modal_email").val(colEmail);
        $("#modal_password").val(colPass);

        $('#userEdit').modal('show');
    });
});