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

function pasteSnippet(index) {
    editor.insert(snippets[index]);
}

function grab_form() {
    let arr = {};
    arr["action_body_text"] = editor.getValue();
    arr["action_desc_text"] = str_toHex($("#action_desc_text").val());
    arr["action_name_text"] = str_toHex($("#action_name_text").val());
    return arr;
}

function add_entry() {
   $.ajax({
    url: "/public/api/add_entry_request",
    type: "POST",
    data: grab_form()
   }).done(function() {
       window.location.reload(); // make popup confirm
   });
}

function edit_submit_entry() {
    add_entry();
}