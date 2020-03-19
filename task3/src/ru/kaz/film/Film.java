package ru.kaz.film;

import ru.kaz.film.crew.actors.Actor;
import ru.kaz.film.crew.techcrew.Director;
import ru.kaz.film.crew.techcrew.Producer;
import ru.kaz.film.crew.techcrew.Screenwriter;
import ru.kaz.film.script.Script;
import ru.kaz.film.script.setting.Setting;
import ru.kaz.film.script.character.FilmCharacter;

public class Film {

    private String name;
    private String annotation;
    private Script script;
    private Actor[] actors;
    private Director director;
    private Producer producer;
    private Screenwriter screenwriter;

    public String getAnnotation() {
        return annotation;
    }

    public String getName() {
        return name;
    }

    public Actor[] getActors() { return actors; }

    public Film(String name, String annotation, Script script, Actor[] actor,
                Director director, Producer producer, Screenwriter screenwriter){
        this.name = name;
        this.annotation = annotation;
        this.script = script;
        this.actors = actor;
        this.director = director;
        this.producer = producer;
        this.screenwriter = screenwriter;
    }

    public static void main(String[] args) {
        Film[] filmsList = new Film[3];

        filmsList[0] = new Film("In Bruges","In Bruges annotation",
            new Script("In Bruges script", new FilmCharacter[]{new FilmCharacter(42, "Ray", "male"),
                    new FilmCharacter(46, "Harry", "male")},
                        new Setting(2010, "winter", "Bruges")), new Actor[]{new Actor("Colin Farrell", 44),
                            new Actor("Ralph Fiennes\n", 48)}, new Director("Martin McDonagh", 56),
                                    new Producer("Jeff Abberley", 60), new Screenwriter("Martin McDonagh", 56));

        filmsList[1] = new Film("The Gentlemen","The Gentlemen annotation",
            new Script("The Gentlemen script", new FilmCharacter[]{new FilmCharacter(38, "Mickey Pearson", "male"),
                    new FilmCharacter(36, "Ray", "male")},
                        new Setting(2019, "summer", "London")), new Actor[]{new Actor("Matthew McConaughey", 44),
                            new Actor("Charlie Hunnam", 40)}, new Director("Guy Ritchie", 49),
                                new Producer("Bill Block", 60), new Screenwriter("Ivan Atkinson", 45));

        filmsList[2] = new Film("Blade Runner","Blade Runner annotation",
                new Script("Blade Runner script", new FilmCharacter[]{new FilmCharacter(39, "Roy Batty", "male"),
                        new FilmCharacter(42, "Rick Deckard", "male")},
                        new Setting(2019, "summer", "Hong Kong")), new Actor[]{new Actor("Harrison Ford", 70),
                new Actor("Rutger Hauer", 73)}, new Director("Ridley Scott", 68),
                new Producer("Charles de Lauzirika", 64), new Screenwriter("Hampton Fancher", 63));

        for (Film film:filmsList) {
            System.out.println("Фильм: " + film.getName() + "\nАннотация: " + film.getAnnotation() + "\nСобытия происходят в "
                    + film.script.getSetting().getCity() + " в " + film.script.getSetting().getYear() +  ". На улице " +
                    film.script.getSetting().getSeason() + "\nВ фильме " + film.actors[0].getName() + " играет " +
                    film.script.getFilmCharacter()[0].getName() + ", которому " + film.script.getFilmCharacter()[0].getAge() +
                    " лет и его пол " + film.script.getFilmCharacter()[0].getGender() + ", а " + film.actors[1].getName() +
                    " играет " + film.script.getFilmCharacter()[1].getName() + " которому " + film.script.getFilmCharacter()[1].getAge() +
                    " лет и его пол " + film.script.getFilmCharacter()[1].getGender() + "\nСценарий фильма: " + film.script.getScript() +
                    "\nНад фильмом работали: Режиссер " + film.director.getName() + ", " + film.director.getAge() + " лет" +
                    "\nПродюсер: " + film.producer.getName()  + ", " + film.producer.getAge() + " лет" + "\nСценарист: " +
                    film.screenwriter.getName() + ", " + film.screenwriter.getAge() + " лет" + "\n");
        }


    }


}
