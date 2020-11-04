package converters;

import inputclasses.ActorInput;
import inputclasses.FilmInput;
import inputclasses.FilmsList;
import outputclasses.ActorOutput;
import outputclasses.ActorsList;
import outputclasses.FilmOutput;

/**
 * Конвертер списка фильмов в список актеров.
 */
public class FilmsToActorsConverter {

    /**
     * Список актеров.
     */
    ActorsList actorsList;

    public ActorsList convert(FilmsList filmsList) {

        actorsList = new ActorsList();


        for(FilmInput filmInput : filmsList.getFilms()){
            // проходим по каждому актеру каждого фильма
            for(ActorInput actorInput : filmInput.getActors()){

                // проверяем, был ли добавлен актер из списка фильмов в новый список актеров.
                ActorOutput actorOutput = getIfAdded(actorInput, actorsList);

                if(actorOutput != null){
                    // если да, добавляем этому актеру фильм и роль
                    actorOutput.addFilm(filmInput.getTitle(), actorInput.getRole());
                } else {
                    // если нет, добавляем актера в список актеров
                    actorsList.add(actorInput.getName(), actorInput.getAge(),
                            new FilmOutput(filmInput.getTitle(), actorInput.getRole()));
                }
            }
        }

        return actorsList;
    }

    /**
     * Получение актера, если он уже был добавлен в новый список актеров.
     *
     * @param actorInput актер из старого списка актеров.
     * @param actorsList список актеров.
     * @return актер если был добавлен, null иначе.
     */
    public ActorOutput getIfAdded(ActorInput actorInput, ActorsList actorsList){

        // если список пуст, выходим сразу
        if(actorsList.getActors() == null){
            return null;
        }

        for(ActorOutput actorOutput : actorsList.getActors()){
            // в данном случае сверяем по именам
            if(actorInput.getName().equals(actorOutput.getName())){
                return actorOutput;
            }
        }
        return null;

    }

}
