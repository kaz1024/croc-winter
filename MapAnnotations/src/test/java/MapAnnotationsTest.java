import exceptions.PutMapKeyFailException;
import exceptions.PutMapValueFailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import testclasses.NotAnnotatedClass;
import testclasses.OtherClass;
import testclasses.SomeClass;

import java.util.Map;

@DisplayName("Проверка аннотаций @MapKeyFail и @MapValueFail")
public class MapAnnotationsTest {

    /** Класс, аннотированный @MapKeyFail. */
    SomeClass someClass;

    /** Класс, аннотированный @MapValueFail. */
    OtherClass otherClass;

    /** Класс без аннотаций. */
    NotAnnotatedClass notAnnotatedClass;

    Map<Object, Object> map;

    @DisplayName("Проверка работоспособности расширенного класса.")
    @Test
    public void testExtendedHashMap() {

        Integer number = 3;
        String string = "value";

        map = new HashMapExtension<>();

        map.put(number, string);

        Assertions.assertEquals(string, map.get(number));

    }

    @DisplayName("Проверка аннотации @MapKeyFail")
    @Test
    public void testMapKeyFailAnnotation(){

        map = new HashMapExtension<>();

        // класс аннотированный @MapKeyFail
        someClass = new SomeClass();

        // не аннотированный класс
        notAnnotatedClass = new NotAnnotatedClass();

        // кладем не аннотированные ключ и значение
        Assertions.assertDoesNotThrow(()-> {map.put(notAnnotatedClass, notAnnotatedClass);});

        // кладем неаннотированный ключ, и аннотированное @MapKeyFail значение
        Assertions.assertDoesNotThrow(()-> {map.put(notAnnotatedClass, someClass);});

        // кладем ключ аннотированный @MapKeyFail
        PutMapKeyFailException exception = Assertions.assertThrows(PutMapKeyFailException.class,
                ()-> {map.put(someClass, notAnnotatedClass);});

        Assertions.assertEquals("Can't put this key to map.", exception.getMessage());


    }

    @DisplayName("Проверка аннотации @MapValueFail")
    @Test
    public void testMapValueFailAnnotation() {

        map = new HashMapExtension<>();

        // класс аннотированный @MapValueFail
        otherClass = new OtherClass();

        // не аннотированный класс
        notAnnotatedClass = new NotAnnotatedClass();

        // кладем не аннотированные ключ и значение
        Assertions.assertDoesNotThrow(()-> {map.put(notAnnotatedClass, notAnnotatedClass);});

        // кладем ключ аннотированный @MapValueFail, и не аннотированное значение
        Assertions.assertDoesNotThrow(()-> {map.put(otherClass, notAnnotatedClass);});

        // кладем значение аннотированное @MapValueFail
        PutMapValueFailException exception = Assertions.assertThrows(PutMapValueFailException.class,
                ()-> {map.put(notAnnotatedClass, otherClass);});

        Assertions.assertEquals("Can't put this value to map.", exception.getMessage());

    }

}
