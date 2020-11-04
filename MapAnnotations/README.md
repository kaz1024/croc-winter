# Задание

Создать собственные аннотации @MapKeyFail и @MapValueFail. Создать runtime исключения PutMapKeyFailException и  PutMapValueFailException. Расширить класс HashMap<Object, Object> таким образом, что:
1. если в метод put поступает ключ отмеченный аннотацией @MapKeyFail выбрасывается PutMapKeyFailException. 
2. если в метод put поступает значение отмеченное аннотацией @MapValueFail выбрасывается PutMapValueFailException. 
