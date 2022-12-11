package vexi.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EventManager {
    private static final Map<Class<? extends Event>, ArrayList<EventData>> REGISTRY_MAP = new HashMap<>();

    private static void sortListValue(Class<? extends Event> clazz) {
        ArrayList<EventData> flexibleArray = new ArrayList<>();
        for (byte b : EventPriority.VALUE_ARRAY) {
            for (EventData methodData : REGISTRY_MAP.get(clazz)) {
                if (methodData.priority == b)
                    flexibleArray.add(methodData);
            }
        }
        REGISTRY_MAP.put(clazz, flexibleArray);
    }

    private static boolean isMethodBad(Method method) {
        return ((method.getParameterTypes()).length != 1 || !method.isAnnotationPresent((Class)EventTarget.class));
    }

    private static boolean isMethodBad(Method method, Class<? extends Event> clazz) {
        return (isMethodBad(method) || method.getParameterTypes()[0].equals(clazz));
    }

    public static ArrayList<EventData> get(Class<? extends Event> clazz) {
        return REGISTRY_MAP.get(clazz);
    }

    public static void cleanMap(boolean removeOnlyEmptyValues) {
        Iterator<Map.Entry<Class<? extends Event>, ArrayList<EventData>>> iterator = REGISTRY_MAP.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!removeOnlyEmptyValues || ((ArrayList)((Map.Entry)iterator.next()).getValue()).isEmpty())
                iterator.remove();
        }
    }

    public static void unregister(Object o, Class<? extends Event> clazz) {
        if (REGISTRY_MAP.containsKey(clazz))
            for (EventData methodData : REGISTRY_MAP.get(clazz)) {
                if (methodData.source.equals(o))
                    ((ArrayList)REGISTRY_MAP.get(clazz)).remove(methodData);
            }
        cleanMap(true);
    }

    public static void unregister(Object o) {
        for (ArrayList<EventData> flexibleArray : REGISTRY_MAP.values()) {
            for (int i = flexibleArray.size() - 1; i >= 0; i--) {
                if (((EventData)flexibleArray.get(i)).source.equals(o))
                    flexibleArray.remove(i);
            }
        }
        cleanMap(true);
    }

    public static void register(Method method, Object o) {
        Class<?> clazz = method.getParameterTypes()[0];
        final EventData methodData = new EventData(o, method, ((EventTarget)method.<EventTarget>getAnnotation(EventTarget.class)).value());
        if (!methodData.target.isAccessible())
            methodData.target.setAccessible(true);
        if (REGISTRY_MAP.containsKey(clazz)) {
            if (((ArrayList)REGISTRY_MAP.get(clazz)).contains(methodData)) {
                ((ArrayList<EventData>)REGISTRY_MAP.get(clazz)).add(methodData);
                sortListValue((Class)clazz);
            }
        } else {
            REGISTRY_MAP.put((Class<? extends Event>) clazz, new ArrayList<EventData>() {

            });
        }
    }

    public static void register(Object o, Class<? extends Event> clazz) {
        for (Method method : o.getClass().getMethods()) {
            if (!isMethodBad(method, clazz))
                register(method, o);
        }
    }

    public static void register(Object o) {
        for (Method method : o.getClass().getMethods()) {
            if (!isMethodBad(method))
                register(method, o);
        }
    }
}