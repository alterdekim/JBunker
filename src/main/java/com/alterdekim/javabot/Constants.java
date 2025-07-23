package com.alterdekim.javabot;

public interface Constants {
    String SCRIPT_MESSAGE = "Вам доступны карты действий:\n";
    String REMOVE_PLAYER = "Игрок %s покидает бункер.";
    String ENDVOTE = "Голосование окончено.";
    String DRAW = "Ничья. Никто не уходит из игры.";
    String DRAW_GONE = "Ничья. Из игры уходит случайный игрок.";
    String GROUP_SET = "Чат выбран.";
    String INTERRUPT_GAME = "Игра остановлена.";
    String USER_VOTED = "%s проголосовал.";
    String JOIN_GAME_BTN = "Присоединиться";
    String START_GAME_MSG = "Набор игроков начат. Присоединяйтесь.";
    String JOINED_THE_GAME = "%s добавлен(а) в игру. Всего игроков: %d";
    String THANK_YOU = "Спасибо.";
    String ALREADY_IN_GAME = "Ты уже в игре.";
    String CANT_JOIN_NOT_STARTED = "Не могу добавить вас в игру, тк вы не разрешили вам писать сообщения.";
    String HELP = "Смотрите закреп или обратитесь к администратору.";
    String START_GAME_BTN = "Начать игру";
    String NOT_ADMIN_EXCEPTION = "Вы не администратор";
    String ACCOUNT = "Ваша анкета:\nПол: %s (смертный: %s, мужчина: %s, женщина: %s)\nВозраст: %d\nПрофессия: %s (%s)\nБагаж: %s (%s)\nХобби: %s\nЗдоровье: %s (%s) %d%%, бесплоден: %s";
    String TRUE = "да";
    String FALSE = "нет";
    String PLAYERS_LESS_THAN_ZERO = "Игроков должно быть больше, чем 1.";
    String HOBBY_BTN = "Хобби";
    String WORK_BTN = "Работа";
    String HEALTH_BTN = "Здоровье";
    String AGE_BTN = "Возраст";
    String GENDER_BTN = "Гендер";
    String LUGG_BTN = "Багаж";
    String GENDER_MESAGE = "%s - пол: %s (смертный: %s, мужчина: %s, женщина: %s)";
    String HEALTH_MESSAGE = "%s - здоровье: %s (%s) %d%%, бесплоден(а): %s";
    String SHOW_TIME = "Время выбрать, какую часть анкеты показать в этом раунде?";
    String AGE_MESSAGE = "%s - возраст: %d";
    String HOBBY_MESSAGE = "%s - хобби: %s";
    String WORK_MESSAGE = "%s - профессия: %s (%s)";
    String LUGG_MESSAGE = "%s - багаж: %s (%s)";
    String DAY_MESSAGE_UPPER = "Следующий раунд начался!\nВероятность выжить \uD83D\uDCC8: %d%% (увеличилась на %f%%)";
    String PRESSED_NIGHT = "%s нажал(а)";
    String PRESSED_SCRIPT_NIGHT = "%s использовал(а) карту действий";
    String DAY_MESSAGE_DOWN = "Следующий раунд начался!\nВероятность выжить \uD83D\uDCC9: %d%% (уменьшилась на %f%%)";
    String DAY_MESSAGE = "Следующий раунд начался!\nВероятность выжить: %f%%";
    String END_GAME = "Конец игры.\nВероятность выжить: %f%%";
    String POLL_QUESTION = "Кто в бункер не идёт?";

    String DEATHMATCH = "Deathmatch";

    String PROBABILITY = "Обычная";

    String WIN_MESSAGE = "Поздравляю! Победа за вами!\n%s";

    String LOSE_MESSAGE = "Поздравляю! Вы проиграли!";

    String INFO_MESSAGE = "Вот открытая информация о живых игроках\n";

    String CANT_SEND_NOT_DAY = "Нельзя использовать эту команду во время перерыва.";
    String BUNKER_STATS = "В вашем бункере следующая ситуация:\n%s";
    String RANDOM_HIV = "Затмение венеры (рандомный вич)";
    String RANDOM_HIV_ENABLED = "Венера взошла для игрока %s";
    String CHANGE_WORKS = "Обменяться профессиями";
    String CHANGE_WORKS_TRIGGERED = "Вы обменялись профессиями с игроком %s";

    String SCANNER_CARD = "Сканер";

    String SCANNER_TRIGGERED = "Сканнер нашел скрытую стату игрока %s, %s";

    String SABOTAGE_CARD = "Саботаж";
    String GODS_WILL_CARD = "Божья воля";

    String CHANGE_HOBBY_CARD = "Обменяться хобби";

    String CHANGE_HOBBY_TRIGGERED = "Вы обменялись хобби с игроком %s";

    String CHANGE_LUGGAGE_CARD = "Обменяться багажом";

    String CHANGE_LUGGAGE_TRIGGERED = "Вы обменялись багажом с игроком %s";

    String STEAL_LUGGAGE_CARD = "Украсть багаж";

    String STEAL_LUGGAGE_TRIGGERED = "Вы украли багаж у игрока %s";

    String RESURRECTION_CARD = "Воскресить игрока";

    String SUDDEN_DEATH = "Внезапная смерть";

    String RESURRECTION_CARD_TRIGGERED = "Вы воскресили игрока %s";

    String SUDDEN_DEATH_TRIGGERED = "Вы убили игрока %s";

    String FOOD_SUPPLY_COND = "В бункере есть запас еды на месяц.";

    String POWER_MALFUNCTION = "Генератор проработает от силы 10 дней, а потом сломается";

    String STRUCTURAL_ISSUES = "В стенах бункера есть трещины, землятресение он не переживет.";

    String MEDS_SUPPLY_COND = "В бункере есть запас лекарств";

    String AI_STUFF_COND = "В бункере есть панель управления с ИИ. Главное чтобы не выключился свет.";

    String WHISPERS_COND = "В этом месте есть что-то странное, обитатели иногда говорят, что слышат чужие разговоры за стенами, хотя там никого нет.";

    String AIR_COND = "В бункере что-то источает отвратительный аромат. Здесь даже Шура Стоун не поможет.";

    String MAX_TIE_NONE = "В этом раунде выбывает тот, за кого проголосовало большинство. При ничье никто не выбывает";

    String MAX_TIE_RANDOM = "В этом раунде выбывает тот, за кого проголосовало большинство. При ничье выбывает случайный игрок.";

    String LEAST_VOTES_OUT = "В этом раунде выбывает тот, за кого меньше всего голосов отдали.";
}