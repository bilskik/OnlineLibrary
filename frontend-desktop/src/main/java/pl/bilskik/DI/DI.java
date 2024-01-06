package pl.bilskik.DI;

public interface DI {
    void register(Class<?> clazz, Object instance);
    <T> T resolve(Class<T> clazz);
}
