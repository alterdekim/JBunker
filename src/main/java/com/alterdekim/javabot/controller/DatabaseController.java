package com.alterdekim.javabot.controller;

import com.alterdekim.javabot.bot.SectionType;
import com.alterdekim.javabot.dto.SynergyResult;
import com.alterdekim.javabot.entities.*;
import com.alterdekim.javabot.service.*;
import com.alterdekim.javabot.util.HashUtils;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class DatabaseController {
    private final BioService bioService;
    private final HealthService healthService;
    private final HobbyService hobbyService;
    private final LuggageService luggageService;
    private final WorkService workService;
    private final TextDataValService textDataValService;
    private final DisasterService disasterService;
    private final SynergyService synergyService;
    private final ActionScriptsService actionService;
    private final ActionRequestService actionRequestService;
    private final GameThemeService themeService;

    private void saveGender(Map<String, String> params) {
        Boolean canDie = Boolean.parseBoolean(params.get("canDie"));
        Boolean ismale = Boolean.parseBoolean(params.get("ismale"));
        Boolean isfemale = Boolean.parseBoolean(params.get("isfemale"));
        String gender_text = new String(HashUtils.decodeHexString(params.get("gender_text")));
        TextDataVal t = textDataValService.save(new TextDataVal(gender_text));
        Long themeId = Long.parseLong(params.get("object_selected_theme"));
        bioService.saveBio(new Bio(ismale, isfemale, canDie, t.getId(), themeId));
    }

    private void editGender(Long id, Map<String, String> params) {
        Boolean canDie = Boolean.parseBoolean(params.get("canDie"));
        Boolean ismale = Boolean.parseBoolean(params.get("ismale"));
        Boolean isfemale = Boolean.parseBoolean(params.get("isfemale"));
        String gender_text = new String(HashUtils.decodeHexString(params.get("gender_text")));
        TextDataVal t = textDataValService.save(new TextDataVal(gender_text));
        Long themeId = Long.parseLong(params.get("object_selected_theme"));
        bioService.updateBio(id, ismale, isfemale, canDie, t.getId(), themeId);
    }

    private void saveHobby(Map<String, String> params) {
        Float powerRange = Float.parseFloat(params.get("powerRange"));
        Float violenceRange = Float.parseFloat(params.get("violenceRange"));
        Float healRange = Float.parseFloat(params.get("healRange"));
        Float foodRange = Float.parseFloat(params.get("foodRange"));
        String hobby_text = new String(HashUtils.decodeHexString(params.get("hobby_text")));
        TextDataVal t = textDataValService.save(new TextDataVal(hobby_text));

        Long themeId = Long.parseLong(params.get("object_selected_theme"));

        hobbyService.saveHobby(new Hobby(foodRange, powerRange, violenceRange, healRange, t.getId(), themeId));
    }

    private void editHobby(Long id, Map<String, String> params) {
        Float powerRange = Float.parseFloat(params.get("powerRange"));
        Float violenceRange = Float.parseFloat(params.get("violenceRange"));
        Float healRange = Float.parseFloat(params.get("healRange"));
        Float foodRange = Float.parseFloat(params.get("foodRange"));
        String hobby_text = new String(HashUtils.decodeHexString(params.get("hobby_text")));
        TextDataVal t = textDataValService.save(new TextDataVal(hobby_text));

        Long themeId = Long.parseLong(params.get("object_selected_theme"));

        hobbyService.updateHobby(id, foodRange, powerRange, violenceRange, healRange, t.getId(), themeId);
    }

    private void saveLuggage(Map<String, String> params) {
        Float powerRange = Float.parseFloat(params.get("powerRange"));
        Float violenceRange = Float.parseFloat(params.get("violenceRange"));
        Float healRange = Float.parseFloat(params.get("healRange"));
        Float foodRange = Float.parseFloat(params.get("foodRange"));
        Boolean isGarbage = Boolean.parseBoolean(params.get("isgarbage"));

        String name_text = new String(HashUtils.decodeHexString(params.get("luggage_name_text")));
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));

        String desc_text = new String(HashUtils.decodeHexString(params.get("luggage_desc_text")));
        TextDataVal t2 = textDataValService.save(new TextDataVal(desc_text));

        Long themeId = Long.parseLong(params.get("object_selected_theme"));

        luggageService.saveLuggage(new Luggage(violenceRange, powerRange, healRange, foodRange, isGarbage, t1.getId(), t2.getId(), themeId));
    }

    private void editLuggage(Long id, Map<String, String> params) {
        Float powerRange = Float.parseFloat(params.get("powerRange"));
        Float violenceRange = Float.parseFloat(params.get("violenceRange"));
        Float healRange = Float.parseFloat(params.get("healRange"));
        Float foodRange = Float.parseFloat(params.get("foodRange"));
        Boolean isGarbage = Boolean.parseBoolean(params.get("isgarbage"));

        String name_text = new String(HashUtils.decodeHexString(params.get("luggage_name_text")));
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));

        String desc_text = new String(HashUtils.decodeHexString(params.get("luggage_desc_text")));
        TextDataVal t2 = textDataValService.save(new TextDataVal(desc_text));

        Long themeId = Long.parseLong(params.get("object_selected_theme"));

        luggageService.updateLuggage(id, violenceRange, powerRange, healRange, foodRange, isGarbage, t1.getId(), t2.getId(), themeId);
    }

    private void saveHealth(Map<String, String> params) {
        Float health_index = Float.parseFloat(params.get("health_index"));
        Boolean childFree = Boolean.parseBoolean(params.get("childFree"));
        String name_text = new String(HashUtils.decodeHexString(params.get("heal_name_text")));
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));

        String desc_text = new String(HashUtils.decodeHexString(params.get("heal_desc_text")));
        TextDataVal t2 = textDataValService.save(new TextDataVal(desc_text));

        Long themeId = Long.parseLong(params.get("object_selected_theme"));

        healthService.saveHealth(new Health(health_index, t1.getId(), t2.getId(), childFree, themeId));
    }

    private void editHealth(Long id, Map<String, String> params) {
        Float health_index = Float.parseFloat(params.get("health_index"));
        Boolean childFree = Boolean.parseBoolean(params.get("childFree"));
        String name_text = new String(HashUtils.decodeHexString(params.get("heal_name_text")));
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));

        String desc_text = new String(HashUtils.decodeHexString(params.get("heal_desc_text")));
        TextDataVal t2 = textDataValService.save(new TextDataVal(desc_text));

        Long themeId = Long.parseLong(params.get("object_selected_theme"));

        healthService.updateHealth(id, health_index, t1.getId(), t2.getId(), childFree, themeId);
    }

    private void saveTheme(Map<String, String> params) {
        String name_text = new String(HashUtils.decodeHexString(params.get("theme_name_text")));
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));
        themeService.saveGameTheme(new GameTheme(t1.getId()));
    }

    private void saveWork(Map<String, String> params) {
        Float powerRange = Float.parseFloat(params.get("powerRange"));
        Float violenceRange = Float.parseFloat(params.get("violenceRange"));
        Float healRange = Float.parseFloat(params.get("healRange"));
        Float foodRange = Float.parseFloat(params.get("foodRange"));

        String name_text = new String(HashUtils.decodeHexString(params.get("work_name_text")));
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));

        String desc_text = new String(HashUtils.decodeHexString(params.get("work_desc_text")));
        TextDataVal t2 = textDataValService.save(new TextDataVal(desc_text));

        Long themeId = Long.parseLong(params.get("object_selected_theme"));

        workService.saveWork(new Work(healRange, powerRange, violenceRange, foodRange, t1.getId(), t2.getId(), themeId));
    }

    private void editWork(Long id, Map<String, String> params) {
        Float powerRange = Float.parseFloat(params.get("powerRange"));
        Float violenceRange = Float.parseFloat(params.get("violenceRange"));
        Float healRange = Float.parseFloat(params.get("healRange"));
        Float foodRange = Float.parseFloat(params.get("foodRange"));

        String name_text = new String(HashUtils.decodeHexString(params.get("work_name_text")));
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));

        String desc_text = new String(HashUtils.decodeHexString(params.get("work_desc_text")));
        TextDataVal t2 = textDataValService.save(new TextDataVal(desc_text));

        Long themeId = Long.parseLong(params.get("object_selected_theme"));

        workService.updateWork(id, healRange, powerRange, violenceRange, foodRange, t1.getId(), t2.getId(), themeId);
    }

    private void saveDiss(Map<String, String> params) {
        String name_text = new String(HashUtils.decodeHexString(params.get("diss_name_text")));
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));

        String desc_text = new String(HashUtils.decodeHexString(params.get("diss_desc_text")));
        TextDataVal t2 = textDataValService.save(new TextDataVal(desc_text));

        Long themeId = Long.parseLong(params.get("object_selected_theme"));

        disasterService.saveDisaster(new Disaster(t1.getId(), t2.getId(), themeId));
    }

    private void editDiss(Long id, Map<String, String> params) {
        String name_text = new String(HashUtils.decodeHexString(params.get("diss_name_text")));
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));

        String desc_text = new String(HashUtils.decodeHexString(params.get("diss_desc_text")));
        TextDataVal t2 = textDataValService.save(new TextDataVal(desc_text));

        Long themeId = Long.parseLong(params.get("object_selected_theme"));

        disasterService.updateDisaster(id, t1.getId(), t2.getId(), themeId);
    }

    private void saveAction(Map<String, String> params) {
        String scriptBody = params.get("action_body_text");
        String name_text = new String(HashUtils.decodeHexString(params.get("action_name_text")));
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));

        String desc_text = new String(HashUtils.decodeHexString(params.get("action_desc_text")));
        TextDataVal t2 = textDataValService.save(new TextDataVal(desc_text));

        actionService.saveScript(new ActionScript(t1.getId(), t2.getId(), scriptBody));
    }

    private void saveActionRequest(Map<String, String> params) {
        String scriptBody = params.get("action_body_text");
        String name_text = new String(HashUtils.decodeHexString(params.get("action_name_text")));
        String desc_text = new String(HashUtils.decodeHexString(params.get("action_desc_text")));
        actionRequestService.saveScript(new ActionScriptRequest(name_text, desc_text, scriptBody));
    }

    @PostMapping("/api/remove_synergy")
    public String remove_synergy(@RequestParam Map<String, String> params) {
        long id = Long.parseLong(params.get("synergy_id"));
        synergyService.removeById(id);
        return "ok";
    }

    @PostMapping("/api/add_synergy")
    public String add_synergy(@RequestParam Map<String, String> params) {
        Long feid = Long.parseLong(params.get("first_entity_id"));
        SectionType fetype = SectionType.values()[Integer.parseInt(params.get("first_entity_type"))];
        Long seid = Long.parseLong(params.get("second_entity_id"));
        SectionType setype = SectionType.values()[Integer.parseInt(params.get("second_entity_type"))];
        Float probability = Float.parseFloat(params.get("probability"));

        synergyService.saveSynergy(new Synergy(feid, fetype, seid, setype, probability));

        return "ok";
    }

    @PostMapping("/api/get_synergies")
    public String get_synergies(@RequestParam Map<String, String> params) {
        Long id = Long.parseLong(params.get("entity_id"));
        String section = params.get("entity_type");
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Synergy> synergyList = switch (section) {
                case "agge" -> bioService.getSynergies(id);
                case "lugg" -> luggageService.getSynergies(id);
                case "prof" -> workService.getSynergies(id);
                case "heal" -> healthService.getSynergies(id);
                case "hobb" -> hobbyService.getSynergies(id);
                default -> new ArrayList<>();
            };
            List<SynergyResult> results = new ArrayList<>();
            for( Synergy s : synergyList ) {
                String textFirst = getText(s.getFirstType(), s.getFirstEntityId());
                String textSecond = getText(s.getSecondType(), s.getSecondEntityId());
                results.add(new SynergyResult(s.getId(), textFirst, textSecond, s.getFirstType(), s.getSecondType(), s.getProbabilityValue()));
            }
            return mapper.writeValueAsString(results);
        } catch (JacksonException e) {
            log.error(e.getMessage());
        }
        return "ok";
    }

    private String getText(SectionType type, Long feid) {
        return switch (type) {
            case GENDER -> textDataValService.getTextDataValById(bioService.getBioById(feid).getGenderTextId()).getText();
            case HEALTH -> textDataValService.getTextDataValById(healthService.getHealthById(feid).getTextNameId()).getText();
            case HOBBY -> textDataValService.getTextDataValById(hobbyService.getHobbyById(feid).getTextDescId()).getText();
            case LUGGAGE -> textDataValService.getTextDataValById(luggageService.getLuggageById(feid).getTextNameId()).getText();
            case WORK -> textDataValService.getTextDataValById(workService.getWorkById(feid).getTextNameId()).getText();
            default -> "";
        };
    }

    @PostMapping("/api/add_entry")
    public String add_entry(@RequestParam Map<String, String> params) {
        /* additional data, disasters */
        String section = params.get("section");
        switch (section) {
            case "agge" -> saveGender(params);
            case "lugg" -> saveLuggage(params);
            case "prof" -> saveWork(params);
            case "heal" -> saveHealth(params);
            case "hobb" -> saveHobby(params);
            case "actions" -> saveAction(params);
            case "themes" -> saveTheme(params);
            default -> saveDiss(params);
        }
        return "ok";
    }

    @PostMapping("/api/accept_script_request")
    public String accept_script_request(@RequestParam Map<String, String> params) {
        long entry_id = Long.parseLong(params.get("entry_id"));
        ActionScriptRequest req = actionRequestService.getActionScriptById(entry_id);
        String scriptBody = req.getScriptBody();
        String name_text = req.getTextName();
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));
        String desc_text = req.getTextDesc();
        TextDataVal t2 = textDataValService.save(new TextDataVal(desc_text));
        actionService.saveScript(new ActionScript(t1.getId(), t2.getId(), scriptBody));
        actionRequestService.removeById(entry_id);
        return "ok";
    }

    @PostMapping("/public/api/add_entry_request")
    public String add_entry_request(@RequestParam Map<String, String> params) {
        saveActionRequest(params);
        return "ok";
    }

    @PostMapping("/api/remove_entry")
    public String remove_entry(@RequestParam Map<String, String> params) {
        String section = params.get("section");
        long entry_id = Long.parseLong(params.get("entry_id"));
        switch (section) {
            case "agge" -> { bioService.removeById(entry_id); synergyService.removeByEntityId(entry_id, SectionType.AGE); }
            case "hobb" -> { hobbyService.removeById(entry_id); synergyService.removeByEntityId(entry_id, SectionType.HOBBY); }
            case "lugg" -> { luggageService.removeById(entry_id); synergyService.removeByEntityId(entry_id, SectionType.LUGGAGE); }
            case "heal" -> { healthService.removeById(entry_id); synergyService.removeByEntityId(entry_id, SectionType.HEALTH); }
            case "prof" -> { workService.removeById(entry_id); synergyService.removeByEntityId(entry_id, SectionType.WORK); }
            case "actions" -> actionService.removeById(entry_id);
            case "script_request" -> actionRequestService.removeById(entry_id);
            case "themes" -> themeService.removeById(entry_id);
            default -> disasterService.removeById(entry_id);
        }
        return "ok";
    }

    @PostMapping("/api/getTextById")
    public String getText(@RequestParam Map<String, String> params) {
        long l = Long.parseLong(params.get("entry_id"));
        return textDataValService.getTextDataValById(l).getText();
    }

    @PostMapping("/api/get_entries")
    public String getEntries(@RequestParam Map<String, String> params) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return switch (params.get("section")) {
                case "agge" -> mapper.writeValueAsString(bioService.getAllBios());
                case "hobb" -> mapper.writeValueAsString(hobbyService.getAllHobbies());
                case "prof" -> mapper.writeValueAsString(workService.getAllWorks());
                case "heal" -> mapper.writeValueAsString(healthService.getAllHealth());
                case "lugg" -> mapper.writeValueAsString(luggageService.getAllLuggages());
                case "actions" -> mapper.writeValueAsString(actionService.getAllActionScripts());
                case "themes" -> mapper.writeValueAsString(themeService.getAllGameThemes());
                default -> mapper.writeValueAsString(disasterService.getAllDisasters());
            };
        } catch (JacksonException e) {
            log.error(e.getMessage());
        }
        return "error";
    }

    @PostMapping("/api/get_entry")
    public String get_entry(@RequestParam Map<String, String> params) {
        ObjectMapper mapper = new ObjectMapper();
        long l = Long.parseLong(params.get("entry_id"));
        try {
            return switch (params.get("section")) {
                case "agge" -> mapper.writeValueAsString(bioService.getBioById(l));
                case "hobb" -> mapper.writeValueAsString(hobbyService.getHobbyById(l));
                case "prof" -> mapper.writeValueAsString(workService.getWorkById(l));
                case "heal" -> mapper.writeValueAsString(healthService.getHealthById(l));
                case "lugg" -> mapper.writeValueAsString(luggageService.getLuggageById(l));
                case "actions" -> mapper.writeValueAsString(actionService.getActionScriptById(l));
                case "themes" -> mapper.writeValueAsString(themeService.getThemeById(l));
                default -> mapper.writeValueAsString(disasterService.getDisasterById(l));
            };
        } catch (JacksonException e) {
            log.error(e.getMessage());
        }
        return "error";
    }

    @PostMapping("/api/edit_entry")
    public String edit_entry(@RequestParam Map<String, String> params) {
        long l = Long.parseLong(params.get("entry_id"));
        String section = params.get("section");
        switch (section) {
            case "agge" -> editGender(l, params);
            case "lugg" -> editLuggage(l, params);
            case "prof" -> editWork(l, params);
            case "heal" -> editHealth(l, params);
            case "hobb" -> editHobby(l, params);
            case "diss" -> editDiss(l, params);
            default -> log.error("Unknown section");
        }
        return "ok";
    }

    @PostMapping("/api/set_theme")
    public String set_theme(@RequestParam Map<String, String> params) {
        long theme_id = Long.parseLong(params.get("theme_id"));
        boolean state = Boolean.parseBoolean(params.get("selected_state"));
        themeService.setThemeState(theme_id, state);
        return "ok";
    }
}
