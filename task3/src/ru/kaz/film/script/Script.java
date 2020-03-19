package ru.kaz.film.script;


import ru.kaz.film.script.character.FilmCharacter;
import ru.kaz.film.script.setting.Setting;

public class Script {

    private String script;
    private FilmCharacter[] characters;
    private Setting setting;

    public FilmCharacter[] getFilmCharacter() {
        return characters;
    }

    public Setting getSetting() {
        return setting;
    }

    public String getScript() {
        return script;
    }

    public Script(String script, FilmCharacter[] characters, Setting setting) {
        this.script = script;
        this.characters = characters;
        this.setting = setting;

    }
}
