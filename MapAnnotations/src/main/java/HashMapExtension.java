import annotations.MapKeyFail;
import annotations.MapValueFail;
import exceptions.PutMapKeyFailException;
import exceptions.PutMapValueFailException;

import java.util.HashMap;


/**
 * Расширение класса HashMap,
 * не позволяющее добавить(put) ключ отмеченный @MapKeyValue и значение, отмеченное @MapValueFail.
 *
 * @param <K> тип ключа.
 * @param <V> тип значения.
 */
public class HashMapExtension<K, V> extends HashMap<K, V> {

    @Override
    public V put(K key, V value) {

        if (checkKeyAnnotation(key)) {
            throw new PutMapKeyFailException("Can't put this key to map.");
        }

        if (checkValueAnnotation(value)) {
            throw new PutMapValueFailException("Can't put this value to map.");
        }


        return super.put(key, value);
    }

    /**
     * Проверка, содерджит ли ключ аннотацию @MapKeyFail.
     *
     * @param key ключ.
     * @return True, если содержит, False иначе.
     */
    public boolean checkKeyAnnotation(K key){

        return key.getClass().getAnnotation(MapKeyFail.class) != null;

    }

    /**
     * Проверка, содерджит ли значение аннотацию @MapKeyFail.
     *
     * @param value значение.
     * @return True, если содержит, False иначе.
     */
    public boolean checkValueAnnotation(V value){

        return value.getClass().getAnnotation(MapValueFail.class) != null;

    }

}