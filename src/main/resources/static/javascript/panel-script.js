function str_toHex(s) {
    // utf8 to latin1
    var s = unescape(encodeURIComponent(s));
    var h = '';
    for (var i = 0; i < s.length; i++) {
        h += s.charCodeAt(i).toString(16);
    }
    return h;
}

function grab_form() {
    let arr = {};
    $("form#entryForm :input").each(function() {
         var input = $(this);
         if(input.attr("type") == "checkbox") {
            arr[input.attr('id')] = "" + input.is(':checked');
         } else if(input.attr("type") == "text") {
            arr[input.attr('id')] = str_toHex(input.val());
         } else if(input.attr("id") == "action_body_text") {
            arr[input.attr('id')] = input.val();
         } else {
            arr[input.attr('id')] = input.val();
         }
    });
    arr["section"] = new URL(window.location.href).searchParams.get("section") + "";
    return arr;
}

function add_entry_modal() {
    $("#modal_submit_edit").css("display", "none");
    $("#modal_submit_add").css("display", "");

    let _ss = new URL(window.location.href).searchParams.get("section") + "";
    if( _ss == "actions" ) { window.location.href = "/script-editor?script_id=-1"; }
}

function form_agge(jobj) {
    get_text_api(jobj.genderTextId, function(t) {
        $("#gender_text").val(t);
    });
    $("#ismale").prop('checked', jobj.isMale);
    $("#isfemale").prop('checked', jobj.isFemale);
    $("#childFree").prop('checked', jobj.isChildfree);
    $("#canDie").prop('checked', jobj.canDie);
}

function form_hobb(jobj) {
    get_text_api(jobj.textDescId, function(t) {
        $("#hobby_text").val(t);
    });
    $("#violenceRange").val(jobj.violence);
    $("#powerRange").val(jobj.power);
    $("#healRange").val(jobj.asocial);
    $("#foodRange").val(jobj.foodstuffs);
}

function form_lugg(jobj) {
    get_text_api(jobj.textNameId, function(t) {
        $("#luggage_name_text").val(t);
    });
    get_text_api(jobj.textDescId, function(t) {
        $("#luggage_desc_text").val(t);
    });
    $("#violenceRange").val(jobj.violence);
    $("#powerRange").val(jobj.power);
    $("#healRange").val(jobj.asocial);
    $("#foodRange").val(jobj.foodstuffs);
    $("#isgarbage").prop('checked', jobj.garbage);
}

function form_prof(jobj) {
    get_text_api(jobj.textNameId, function(t) {
        $("#work_name_text").val(t);
    });
    get_text_api(jobj.textDescId, function(t) {
        $("#work_desc_text").val(t);
    });
    $("#violenceRange").val(jobj.violence);
    $("#powerRange").val(jobj.power);
    $("#healRange").val(jobj.asocial);
    $("#foodRange").val(jobj.foodstuffs);
}

function form_heal(jobj) {
    get_text_api(jobj.textNameId, function(t) {
        $("#heal_name_text").val(t);
    });
    get_text_api(jobj.textDescId, function(t) {
        $("#heal_desc_text").val(t);
    });
    $("#childFree").prop("checked", jobj.isChildfree);
    $("#health_index").val(jobj.health_index);
}

function form_disaster(jobj) {
    get_text_api(jobj.nameTextId, function(t) {
        $("#diss_name_text").val(t);
    });
    get_text_api(jobj.descTextId, function(t) {
        $("#diss_desc_text").val(t);
    });
}

function form_actions(jobj) {
    get_text_api(jobj.textNameId, function(t) {
        $("#action_name_text").val(t);
    });
    get_text_api(jobj.textDescId, function(t) {
        $("#action_desc_text").val(t);
    });
    //$("#action_body_text").val(jobj.scriptBody);
    editor.setValue(jobj.scriptBody);
}

function show_modal_edit(jobj, oid) {
    var section = new URL(window.location.href).searchParams.get("section");

    switch(section) {
        case "agge":
            form_agge(jobj);
            break;
        case "hobb":
            form_hobb(jobj);
            break;
        case "lugg":
            form_lugg(jobj);
            break;
        case "heal":
            form_heal(jobj);
            break;
        case "prof":
            form_prof(jobj);
            break;
        case "diss":
            form_disaster(jobj);
            break;
        case "actions":
            form_actions(jobj);
            break;
        default:
            form_disaster(jobj);
            break;
    }

    if( section != "actions" ) {
        $("#object_selected_theme option[value="+jobj.theme+"]").prop('selected', true);
    }

    $("#modal_submit_edit").css("display", "");
    $("#modal_submit_add").css("display", "none");
    $("#modal_submit_edit").attr("data-entry-id", oid);
}

function edit_submit_entry(obj) {
    // TODO: change this
    let __a = grab_form();
    __a["entry_id"] = $(obj).attr("data-entry-id");
   $.ajax({
    url: "/api/edit_entry",
    type: "POST",
    data: __a
   }).done(function() {
       window.location.reload();
   });
}

function edit_entry(obj) {
    let __ss = new URL(window.location.href).searchParams.get("section") + "";
    if( __ss == "actions" ) {
        window.location.href = "/script-editor?script_id=" + ($(obj).attr("data-id"));
    } else {
        $.post("/api/get_entry", "section="+__ss+"&entry_id="+($(obj).attr("data-id")), function(data, status) {
            var jobj = JSON.parse(data);
            show_modal_edit(jobj, $(obj).attr("data-id"));
        });
    }
}

function get_text_api(tid, cb) {
    $.post("/api/getTextById", "entry_id="+tid, function(data, status) {
        cb(data);
    });
}

function accept_script_request(obj) {
    $.post("/api/accept_script_request", "entry_id="+($(obj).attr("data-id")), function(data, status) {
        window.location.reload();
    });
}

function remove_entry(obj) {
    $.post("/api/remove_entry", "section="+new URL(window.location.href).searchParams.get("section")+"&entry_id="+($(obj).attr("data-id")), function(data, status) {
        window.location.reload();
    });
}

function add_entry() {
   $.ajax({
    url: "/api/add_entry",
    type: "POST",
    data: grab_form()
   }).done(function() {
       window.location.reload();
   });
}

function add_synergy(obj) {
    let firstEntityId = $(obj).attr("data-id");
    let firstEntityType = 0;
    let secondEntityId = $("#second_select_vals").val();
    let secondEntityType = $("#second_select_types").val();
    let probabilityValue = $("#synergy_prob_input").val();

    let section = new URL(window.location.href).searchParams.get("section");

    switch(section) {
        case "agge":
            firstEntityType = 0;
            break;
        case "hobb":
            firstEntityType = 2;
            break;
        case "lugg":
            firstEntityType = 3;
            break;
        case "heal":
            firstEntityType = 1;
            break;
        case "prof":
            firstEntityType = 4;
            break;
    }
    let _set = 0;
    switch(secondEntityType) {
        case "agge":
            _set = 0;
            break;
        case "hobb":
            _set = 2;
            break;
        case "lugg":
            _set = 3;
            break;
        case "heal":
            _set = 1;
            break;
        case "prof":
            _set = 4;
            break;
    }
    secondEntityType = _set;
    $.post("/api/add_synergy", "first_entity_id="+firstEntityId+"&first_entity_type="+firstEntityType+"&second_entity_id="+secondEntityId+"&second_entity_type="+secondEntityType+"&probability="+probabilityValue, function(data, status) {
        window.location.reload();
    });
}

function show_synergies_modal(obj) {
    $("#add_synergy_button").attr("data-id", $(obj).attr("data-id"));
    $(".table_row_generated").remove();
    $.post("/api/get_synergies", "entity_id="+$(obj).attr("data-id")+"&entity_type="+new URL(window.location.href).searchParams.get("section"), function(data, status) {
        data = JSON.parse(data);
        for( let i = 0; i < data.length; i++ ) {
            $("#table_body_modal").append("<tr class=\"table_row_generated\"><td>"+data[i].firstEntityText+"</td><td>"+data[i].firstType+"</td><td>"+data[i].secondEntityText+"</td><td>"+data[i].secondType+"</td><td>"+data[i].probabilityValue+"</td><td><button type=\"button\" class=\"btn btn-danger\" onclick=\"remove_synergy(this)\" data-synergy-id=\""+data[i].id+"\">Remove</button></td></tr>");
        }
    });
}

function secondTypeChange(obj) {
    $.post("/api/get_entries", "section="+($(obj).val()), function(data, status) {
        $("#second_select_vals").html("");
        let jobj = JSON.parse(data);
        for( let i = 0; i < jobj.length; i++ ) {
            let ind = jobj[i];
            let ss = 0;
            if( ind.genderTextId != undefined ) {
                ss = ind.genderTextId;
            } else if( ind.textNameId != undefined ) {
                ss = ind.textNameId;
            } else if( ind.textDescId != undefined ) {
                ss = ind.textDescId;
            }

            get_text_api(ss, function(rte) {
                $("#second_select_vals").append($('<option>', {
                    value: ind.id,
                    text: rte
                }));
            });
        }
    });
}

function remove_synergy(obj) {
    $.post("/api/remove_synergy", "synergy_id="+($(obj).attr("data-synergy-id")), function(data, status) {
        window.location.reload();
    });
}

$(function() {
    $(".is-selected-theme").each(function() {
        if( $(this).attr("data-selected") == "true" ) {
            $(this).html($("#selected_button_texts").attr("data-selected"));
            $(this).addClass("btn-success");
        } else {
            $(this).html($("#selected_button_texts").attr("data-notselected"));
            $(this).addClass("btn-secondary");
        }
    });

    $(".is-selected-theme").click(function() {
        if( $(this).attr("data-selected") == "true" ) {
            $(this).attr("data-selected", "false");
            $(this).removeClass("btn-success");
            $(this).html($("#selected_button_texts").attr("data-notselected"));
            $(this).addClass("btn-secondary");
        } else {
            $(this).attr("data-selected", "true");
            $(this).removeClass("btn-secondary");
            $(this).html($("#selected_button_texts").attr("data-selected"));
            $(this).addClass("btn-success");
        }
        $.post("/api/set_theme", "theme_id="+($(this).attr("data-id"))+"&selected_state="+($(this).attr("data-selected")), function(data, status) {});
    });
});