package com.alterdekim.javabot.util;

import com.alterdekim.javabot.bot.Player;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
public class LuaSerializer {

   /* public static LuaTable serializeObjectList(List<Player> list) {
        LuaTable table = new LuaTable();
        IntStream.range(0, list.size()).forEach(i -> table.set(i, serializeObject(list.get(i))));
        return table;
    }*/

    public static LuaTable serializeObjectList(List<?> list) {
        LuaTable table = new LuaTable();
        IntStream.range(0, list.size()).forEach(i -> table.set(i, serializeObject(list.get(i))));
        return table;
    }

    public static LuaValue serializeObject(Object o) {
        Map<String, Object> map = new HashMap<>();
        getPrivateFields(o.getClass()).stream()
                .forEach(f -> {
                    try {
                        f.setAccessible(true);
                        String type_name = ((Class) f.getType()).getName();
                        String name = f.getName();
                        switch (type_name) {
                            case "java.lang.Long":
                                map.put(name, ((Long) f.get(o)).longValue());
                                break;
                            case "long":
                                map.put(name, f.getLong(o));
                                break;
                            case "int":
                                map.put(name, f.getInt(o));
                                break;
                            case "java.lang.Integer":
                                map.put(name, ((Integer) f.get(o)).intValue());
                                break;
                            case "float":
                                map.put(name, f.getFloat(o));
                                break;
                            case "java.lang.Float":
                                map.put(name, ((Float) f.get(o)).floatValue());
                                break;
                            case "double":
                                map.put(name, f.getDouble(o));
                                break;
                            case "java.lang.Double":
                                map.put(name, ((Double) f.get(o)).doubleValue());
                                break;
                            case "boolean":
                                map.put(name, f.getBoolean(o));
                                break;
                            case "java.lang.Boolean":
                                map.put(name, ((Boolean) f.get(o)).booleanValue());
                                break;
                            case "java.lang.String":
                                map.put(name, (String) f.get(o));
                                break;
                            case "java.util.List":
                                break;
                            default:
                                map.put(name, serializeObject(f.get(o)));
                                break;
                        }
                    } catch (Exception e) {
                        //log.error(e.getMessage(), e);
                    }
                });
        return convert(map);
    }

    private static LuaValue convert(Map<String, Object> map) {
        LuaTable luaTable = new LuaTable();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // Convert Java object to LuaValue (handle different data types)
            LuaValue luaValue = CoerceJavaToLua.coerce(value);
            luaTable.set(key, luaValue);
        }
        return luaTable;
    }

    private static List<Field> getPrivateFields(Class<?> theClass) {
        List<Field> privateFields = new ArrayList<Field>();
        Field[] fields = theClass.getDeclaredFields();
        for(Field field : fields){
            if(Modifier.isPrivate(field.getModifiers())) {
                privateFields.add(field);
            }
        }
        return privateFields;
    }
}
