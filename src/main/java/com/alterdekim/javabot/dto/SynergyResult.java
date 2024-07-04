package com.alterdekim.javabot.dto;

import com.alterdekim.javabot.bot.SectionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SynergyResult {
    private Long id;
    private String firstEntityText;
    private String secondEntityText;
    private SectionType firstType;
    private SectionType secondType;
    private Float probabilityValue;
}
