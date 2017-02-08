package com.google.android.libraries.remixer;

import com.google.android.libraries.remixer.serialization.SerializedColor;
import com.google.android.libraries.remixer.serialization.ValueConverter;
import com.google.android.libraries.remixer.serialization.converters.BooleanValueConverter;
import com.google.android.libraries.remixer.serialization.converters.ColorValueConverter;
import com.google.android.libraries.remixer.serialization.converters.FloatValueConverter;
import com.google.android.libraries.remixer.serialization.converters.StringValueConverter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * The data type for each RemixerItem. The data type is used to determine default layoutIDs and to
 * help serialization.
 *
 * @param <RuntimeType> The type to use during runtime to represent variables of this DataType
 * @param <SerializableType> The type to use to serialize variables of this type.
 */
public class DataType<RuntimeType, SerializableType> {

  /**
   * The serializable, unique name for this data type.
   */
  private final String name;

  /**
   * The runtime class of the values contained by this variable.
   */
  private final Class<RuntimeType> runtimeType;

  /**
   * The serializable class of the values contained by this variable.
   */
  private final Class<SerializableType> serializableType;

  /**
   * The value converter that aids in the serialization process.
   */
  private final ValueConverter<RuntimeType, SerializableType> converter;

  /**
   * Map of default layout ids for this datatype when used with a specific RemixerItem class.
   *
   * <p>The key for this map is the specific RemixerItem subclass, and the value is the default
   * layout to use when a RemixerItem of the specific subclass has this data type.
   */
  private final Map<Class<? extends Variable>, Integer> layoutIdForVariableType =
      new HashMap<>();

  /**
   * Constructs a datatype with the given {@code name}, that takes values of type {@code runtimeType}
   * and uses {@code converter} to serialize.
   *
   * <p>Note {@code converter} has a {@link ValueConverter#dataType} field that must be initialized
   * to the same as {@code name}.
   */
  public DataType(
      String name,
      Class<RuntimeType> runtimeType,
      Class<SerializableType> serializableType,
      ValueConverter<RuntimeType, SerializableType> converter) {
    this.name = name;
    this.runtimeType = runtimeType;
    this.serializableType = serializableType;
    this.converter = converter;
    if (!name.equals(converter.getDataType())) {
      throw new AssertionError(String.format(
          Locale.getDefault(),
          "The data type %s has a converter whose data type doesn't match, %s",
          name, converter.getDataType()));
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    DataType dataType = (DataType) obj;
    if (!name.equals(dataType.name)) {
      return false;
    }
    return runtimeType.equals(dataType.runtimeType);

  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + runtimeType.hashCode();
    return result;
  }

  public void setLayoutIdForVariableType(Class<? extends Variable> clazz, int layoutId) {
    layoutIdForVariableType.put(clazz, layoutId);
  }

  public int getLayoutIdForVariableType(Class<? extends Variable> clazz) {
    return layoutIdForVariableType.get(clazz);
  }

  public String getName() {
    return name;
  }

  public Class<RuntimeType> getRuntimeType() {
    return runtimeType;
  }

  public Class<SerializableType> getSerializableType() {
    return serializableType;
  }

  public ValueConverter<RuntimeType, SerializableType> getConverter() {
    return converter;
  }

  // ======= Default data types defined here.
  private static final String KEY_BOOLEAN = "__DataTypeBoolean__";
  private static final String KEY_COLOR = "__DataTypeColor__";
  private static final String KEY_NUMBER = "__DataTypeNumber__";
  private static final String KEY_STRING = "__DataTypeString__";

  public static final DataType<Boolean, Boolean> BOOLEAN = new DataType<>(
      KEY_BOOLEAN, Boolean.class, Boolean.class, new BooleanValueConverter(KEY_BOOLEAN));

  public static final DataType<Integer, SerializedColor> COLOR = new DataType<>(
      KEY_COLOR, Integer.class, SerializedColor.class, new ColorValueConverter(KEY_COLOR));

  public static final DataType<Float, Float> NUMBER = new DataType<>(
      KEY_NUMBER, Float.class, Float.class, new FloatValueConverter(KEY_NUMBER));

  public static final DataType<String, String> STRING = new DataType<>(
      KEY_STRING, String.class, String.class, new StringValueConverter(KEY_STRING));
}
