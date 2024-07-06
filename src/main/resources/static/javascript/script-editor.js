var editor = ace.edit("script_editor");
editor.setTheme("ace/theme/github");
editor.session.setMode("ace/mode/lua");

// set script body
// editor.setValue(jobj.scriptBody);

const snippets = ["player", "players[index]", "genders[index]", "hobbies[index]", "healths[index]", "luggages[index]", "works[index]"];

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
    $.post("/api/edit_entry", "section="+_ss+"&entry_id="+($.urlParam("script_id")), function(data, status) {
        var jobj = JSON.parse(data);
        editor.setValue(jobj.scriptBody);
    });
}

if ($.urlParam("script_id") != "-1") {
    getActionScript();
}