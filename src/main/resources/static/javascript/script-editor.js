var editor = ace.edit("script_editor");
editor.setTheme("ace/theme/github");
editor.session.setMode("ace/mode/lua");

// set script body
// editor.setValue(jobj.scriptBody);

const snippets = ["player", "players[index]", "genders[index]", "hobbies[index]", "healths[index]", "luggages[index]", "works[index]"];

function str_toHex(s) {
    // utf8 to latin1
    var s = unescape(encodeURIComponent(s));
    var h = '';
    for (var i = 0; i < s.length; i++) {
        h += s.charCodeAt(i).toString(16);
    }
    return h;
}

$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null) {
       return null;
    }
    return decodeURI(results[1]) || 0;
};

function pasteSnippet(index) {
    editor.insert(snippets[index]);
}

function getActionScript() {
    $.post("/api/edit_entry", "section=actions&entry_id="+($.urlParam("script_id")), function(data, status) {
        var jobj = JSON.parse(data);
        editor.setValue(jobj.scriptBody);
    });
}

$( document ).ready(function() {
    if ($.urlParam("script_id") != "-1") {
        getActionScript();
    }
});

function grab_form() {
    let arr = [];
    arr["action_body_text"] = editor.getValue();
    arr["action_desc_text"] = str_toHex($("#action_desc_text").val());
    arr["action_name_text"] = str_toHex($("#action_name_text").val());
    arr["section"] = "actions";
    return arr;
}

function add_entry() {
   $.ajax({
    url: "/api/add_entry",
    type: "POST",
    data: grab_form()
   }).done(function() {
       window.location.href = "/panel?section=actions";
   });
}

function edit_submit_entry() {
    $.post("/api/remove_entry", "section=actions&entry_id="+($.urlParam("script_id")), function(data, status) {
        add_entry();
    });
}