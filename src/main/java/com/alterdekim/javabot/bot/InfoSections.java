package com.alterdekim.javabot.bot;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class InfoSections {
    private final List<InfoSection> sections;

    public InfoSections() {
        this.sections = new ArrayList<>();
    }

    public boolean isShowed(SectionType type) {
        return this.sections.stream().anyMatch(p -> p.getType() == type);
    }

    public void pushShowedState(SectionType type) {
        if(this.isShowed(type)) return;
        this.sections.add(new InfoSection(type));
    }

    public void clear() {
        this.sections.clear();
    }
}
