package pl.bilskik.DI;

import java.util.HashMap;
import java.util.Map;

public class DIContainer implements DI {
    private static DIContainer diContainer;
    private Map<Class<?>, Object> dependencies= new HashMap<>();

    private DIContainer() {}

    public static DIContainer getInstance() {
        if(diContainer == null) {
            diContainer = new DIContainer();
        }
        return diContainer;
    }
    @Override
    public void register(Class<?> clazz, Object instance) {
        dependencies.put(clazz, instance);
    }

    @Override
    public <T> T resolve(Class<T> clazz) {
        return (T) dependencies.get(clazz);
    }
}
