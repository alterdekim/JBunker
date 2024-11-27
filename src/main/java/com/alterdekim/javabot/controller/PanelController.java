package com.alterdekim.javabot.controller;

import com.alterdekim.javabot.entities.*;
import com.alterdekim.javabot.service.*;
import com.alterdekim.javabot.util.UAgentInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@AllArgsConstructor
public class PanelController {
    private final BioService bioService;
    private final HealthService healthService;
    private final HobbyService hobbyService;
    private final LuggageService luggageService;
    private final WorkService workService;
    private final TextDataValService textDataValService;
    private final DisasterService disasterService;
    private final ActionScriptsServiceImpl scriptsService;
    private final ActionRequestServiceImpl actionRequestService;
    private final GameThemeServiceImpl gameThemeService;

    private List<Card> dissToCards() {
        List<Disaster> bios = disasterService.getAllDisasters();
        List<Card> cards = new ArrayList<>();
        for( Disaster b : bios ) {
            Card card = new Card();
            card.setId(b.getId());
            card.setTitle(textDataValService.getTextDataValById(b.getNameTextId()).getText());
            card.setBody(List.of("Description: " + textDataValService.getTextDataValById(b.getDescTextId()).getText(),
                    "Theme: " + textDataValService.getTextDataValById(gameThemeService.getThemeById(b.getTheme()).getTextNameId()).getText()));
            cards.add(card);
        }
        cards.sort(Comparator.comparing(Card::getId));
        Collections.reverse(cards);
        return cards;
    }

    private List<Card> bioToCards() {
        List<Bio> bios = bioService.getAllBios();
        List<Card> cards = new ArrayList<>();
        for( Bio b : bios ) {
            Card card = new Card();
            card.setId(b.getId());
            card.setTitle(textDataValService.getTextDataValById(b.getGenderTextId()).getText());
            card.setBody(Arrays.asList("canDie: " + b.getCanDie(), "isMale: " + b.getIsMale(), "isFemale: " + b.getIsFemale(),
                    "Theme: " + textDataValService.getTextDataValById(gameThemeService.getThemeById(b.getTheme()).getTextNameId()).getText()));
            cards.add(card);
        }
        cards.sort(Comparator.comparing(Card::getId));
        Collections.reverse(cards);
        return cards;
    }

    private List<Card> hobbyToCards() {
        List<Hobby> bios = hobbyService.getAllHobbies();
        List<Card> cards = new ArrayList<>();
        for( Hobby b : bios ) {
            Card card = new Card();
            card.setId(b.getId());
            card.setTitle(textDataValService.getTextDataValById(b.getTextDescId()).getText());
            card.setBody(Arrays.asList("Foodstuffs: " + b.getFoodstuffs(), "Asocial: " + b.getAsocial(), "Power: " + b.getPower(), "Violence:" + b.getViolence(),
                    "Theme: " + textDataValService.getTextDataValById(gameThemeService.getThemeById(b.getTheme()).getTextNameId()).getText()));
            cards.add(card);
        }
        cards.sort(Comparator.comparing(Card::getId));
        Collections.reverse(cards);
        return cards;
    }

    private List<Card> workToCards() {
        List<Work> bios = workService.getAllWorks();
        List<Card> cards = new ArrayList<>();
        for( Work b : bios ) {
            Card card = new Card();
            card.setId(b.getId());
            card.setTitle(textDataValService.getTextDataValById(b.getTextNameId()).getText());
            card.setBody(Arrays.asList("Foodstuffs: " + b.getFoodstuffs(), "Power: " + b.getPower(), "Violence: " + b.getViolence(), "Asocial: " + b.getAsocial(), "Description: " + textDataValService.getTextDataValById(b.getTextDescId()).getText(),
                    "Theme: " + textDataValService.getTextDataValById(gameThemeService.getThemeById(b.getTheme()).getTextNameId()).getText()));
            cards.add(card);
        }
        cards.sort(Comparator.comparing(Card::getId));
        Collections.reverse(cards);
        return cards;
    }

    private List<Card> healToCards() {
        List<Health> bios = healthService.getAllHealth();
        List<Card> cards = new ArrayList<>();
        for( Health b : bios ) {
            Card card = new Card();
            card.setId(b.getId());
            card.setTitle(textDataValService.getTextDataValById(b.getTextNameId()).getText());
            card.setBody(Arrays.asList("Health index: " + b.getHealth_index(),"isChildfree: " + b.getIsChildfree(), "Description: " + textDataValService.getTextDataValById(b.getTextDescId()).getText(),
                    "Theme: " + textDataValService.getTextDataValById(gameThemeService.getThemeById(b.getTheme()).getTextNameId()).getText()));
            cards.add(card);
        }
        cards.sort(Comparator.comparing(Card::getId));
        Collections.reverse(cards);
        return cards;
    }

    private List<Card> luggageToCards() {
        List<Luggage> bios = luggageService.getAllLuggages();
        List<Card> cards = new ArrayList<>();
        for( Luggage b : bios ) {
            Card card = new Card();
            card.setId(b.getId());
            card.setTitle(textDataValService.getTextDataValById(b.getTextNameId()).getText());
            card.setBody(Arrays.asList("Foodstuffs: " + b.getFoodstuffs(),
                    "Power: " + b.getPower(),
                    "Asocial: " + b.getAsocial(),
                    "Violence: " + b.getViolence(),
                    "Is garbage: " + b.getGarbage(),
                    "Description: " + textDataValService.getTextDataValById(b.getTextDescId()).getText(),
                    "Theme: " + textDataValService.getTextDataValById(gameThemeService.getThemeById(b.getTheme()).getTextNameId()).getText()
            ));
            cards.add(card);
        }
        cards.sort(Comparator.comparing(Card::getId));
        Collections.reverse(cards);
        return cards;
    }

    private List<Card> actionsToCards() {
        List<ActionScript> scripts = scriptsService.getAllActionScripts();
        List<Card> cards = new ArrayList<>();
        for( ActionScript b : scripts ) {
            Card card = new Card();
            card.setId(b.getId());
            card.setTitle(textDataValService.getTextDataValById(b.getTextNameId()).getText());
            card.setBody(Arrays.asList("Script body hidden."));
            cards.add(card);
        }
        cards.sort(Comparator.comparing(Card::getId));
        Collections.reverse(cards);
        return cards;
    }

    private List<Card> requestsToCards() {
        List<ActionScriptRequest> scriptRequests = actionRequestService.getAllActionScripts();
        List<Card> cards = new ArrayList<>();
        for( ActionScriptRequest b : scriptRequests ) {
            Card card = new Card();
            card.setId(b.getId());
            card.setTitle(b.getTextName());
            card.setBody(new ArrayList<>(Arrays.asList(b.getScriptBody().split("\n"))));
            cards.add(card);
        }
        cards.sort(Comparator.comparing(Card::getId));
        Collections.reverse(cards);
        return cards;
    }

    private List<Card> themesToCards() {
        List<GameTheme> themeList = gameThemeService.getAllGameThemes();
        List<Card> cards = new ArrayList<>();
        for( GameTheme theme : themeList ) {
            Card card = new Card();
            card.setId(theme.getId());
            card.setTitle(textDataValService.getTextDataValById(theme.getTextNameId()).getText());
            card.setBody(new ArrayList<>());
            card.setSelected(theme.getIsSelected());
            cards.add(card);
        }
        return cards;
    }

    private List<List<Card>> toPairs(List<Card> cards) {
        List<List<Card>> l = IntStream.range(0, cards.size())
                .filter(i -> i % 2 == 0 && i + 1 < cards.size())
                .boxed()
                .map(i -> List.of(cards.get(i), cards.get(i+1)))
                .collect(Collectors.toList());
        if( cards.size() % 2 == 1 ) { l.add(Collections.singletonList(cards.get(cards.size()-1))); }
        return l;
    }

    @GetMapping("/panel")
    public String panelPage(Model model, @RequestHeader("User-Agent") String uagent, @RequestHeader("Accept") String accepth, @RequestParam(value = "section", defaultValue = "diss") String section) {
        boolean is_mobile = new UAgentInfo(uagent, accepth).detectSmartphone();
        model.addAttribute("is_mobile", is_mobile);
        model.addAttribute("section", section);
        switch (section) {
            case "lugg":
                model.addAttribute( "cards", is_mobile ? luggageToCards() : toPairs(luggageToCards()) );
                break;
            case "heal":
                model.addAttribute("cards", is_mobile ? healToCards() : toPairs(healToCards()) );
                break;
            case "prof":
                model.addAttribute("cards", is_mobile ? workToCards() : toPairs(workToCards()) );
                break;
            case "hobb":
                model.addAttribute("cards", is_mobile ? hobbyToCards() : toPairs(hobbyToCards()) );
                break;
            case "agge":
                model.addAttribute("cards", is_mobile ? bioToCards() : toPairs(bioToCards()) );
                break;
            case "diss":
                model.addAttribute("cards", is_mobile ? dissToCards() : toPairs(dissToCards()) );
                break;
            case "stats":
                // !
                break;
            case "actions":
                model.addAttribute("cards", is_mobile ? actionsToCards() : toPairs(actionsToCards()) );
                break;
            case "script_request":
                model.addAttribute("cards", is_mobile ? requestsToCards() : toPairs(requestsToCards()) );
                break;
            case "themes":
                model.addAttribute("cards", is_mobile ? themesToCards() : toPairs(themesToCards()));
                break;
        }
        return "panel";
    }

    @GetMapping("/script-editor")
    public String scriptEditorAdmin(Model model, @RequestHeader("User-Agent") String uagent, @RequestHeader("Accept") String accepth) {
        model.addAttribute("is_mobile", new UAgentInfo(uagent, accepth).detectSmartphone());
        return "script-editor";
    }

    @GetMapping("/editor-public")
    public String scriptEditorPublic(Model model, @RequestHeader("User-Agent") String uagent, @RequestHeader("Accept") String accepth) {
        model.addAttribute("is_mobile", new UAgentInfo(uagent, accepth).detectSmartphone());
        return "editor-public";
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class Card implements Serializable {
        private String title;
        private List<String> body;
        private Long id;
        private Boolean selected = false;
    }
}
