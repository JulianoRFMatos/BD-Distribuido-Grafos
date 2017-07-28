/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package GrafoBD;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-07-27")
public class Aresta implements org.apache.thrift.TBase<Aresta, Aresta._Fields>, java.io.Serializable, Cloneable, Comparable<Aresta> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Aresta");

  private static final org.apache.thrift.protocol.TField FIRST_VERT_FIELD_DESC = new org.apache.thrift.protocol.TField("firstVert", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField SECOND_VERT_FIELD_DESC = new org.apache.thrift.protocol.TField("secondVert", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField PESO_FIELD_DESC = new org.apache.thrift.protocol.TField("peso", org.apache.thrift.protocol.TType.DOUBLE, (short)3);
  private static final org.apache.thrift.protocol.TField FLAG_FIELD_DESC = new org.apache.thrift.protocol.TField("flag", org.apache.thrift.protocol.TType.BOOL, (short)4);
  private static final org.apache.thrift.protocol.TField DESCRICAO_FIELD_DESC = new org.apache.thrift.protocol.TField("descricao", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ArestaStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ArestaTupleSchemeFactory();

  public int firstVert; // required
  public int secondVert; // required
  public double peso; // required
  public boolean flag; // required
  public java.lang.String descricao; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FIRST_VERT((short)1, "firstVert"),
    SECOND_VERT((short)2, "secondVert"),
    PESO((short)3, "peso"),
    FLAG((short)4, "flag"),
    DESCRICAO((short)5, "descricao");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // FIRST_VERT
          return FIRST_VERT;
        case 2: // SECOND_VERT
          return SECOND_VERT;
        case 3: // PESO
          return PESO;
        case 4: // FLAG
          return FLAG;
        case 5: // DESCRICAO
          return DESCRICAO;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __FIRSTVERT_ISSET_ID = 0;
  private static final int __SECONDVERT_ISSET_ID = 1;
  private static final int __PESO_ISSET_ID = 2;
  private static final int __FLAG_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FIRST_VERT, new org.apache.thrift.meta_data.FieldMetaData("firstVert", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SECOND_VERT, new org.apache.thrift.meta_data.FieldMetaData("secondVert", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PESO, new org.apache.thrift.meta_data.FieldMetaData("peso", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.FLAG, new org.apache.thrift.meta_data.FieldMetaData("flag", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.DESCRICAO, new org.apache.thrift.meta_data.FieldMetaData("descricao", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Aresta.class, metaDataMap);
  }

  public Aresta() {
  }

  public Aresta(
    int firstVert,
    int secondVert,
    double peso,
    boolean flag,
    java.lang.String descricao)
  {
    this();
    this.firstVert = firstVert;
    setFirstVertIsSet(true);
    this.secondVert = secondVert;
    setSecondVertIsSet(true);
    this.peso = peso;
    setPesoIsSet(true);
    this.flag = flag;
    setFlagIsSet(true);
    this.descricao = descricao;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Aresta(Aresta other) {
    __isset_bitfield = other.__isset_bitfield;
    this.firstVert = other.firstVert;
    this.secondVert = other.secondVert;
    this.peso = other.peso;
    this.flag = other.flag;
    if (other.isSetDescricao()) {
      this.descricao = other.descricao;
    }
  }

  public Aresta deepCopy() {
    return new Aresta(this);
  }

  @Override
  public void clear() {
    setFirstVertIsSet(false);
    this.firstVert = 0;
    setSecondVertIsSet(false);
    this.secondVert = 0;
    setPesoIsSet(false);
    this.peso = 0.0;
    setFlagIsSet(false);
    this.flag = false;
    this.descricao = null;
  }

  public int getFirstVert() {
    return this.firstVert;
  }

  public Aresta setFirstVert(int firstVert) {
    this.firstVert = firstVert;
    setFirstVertIsSet(true);
    return this;
  }

  public void unsetFirstVert() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __FIRSTVERT_ISSET_ID);
  }

  /** Returns true if field firstVert is set (has been assigned a value) and false otherwise */
  public boolean isSetFirstVert() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __FIRSTVERT_ISSET_ID);
  }

  public void setFirstVertIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __FIRSTVERT_ISSET_ID, value);
  }

  public int getSecondVert() {
    return this.secondVert;
  }

  public Aresta setSecondVert(int secondVert) {
    this.secondVert = secondVert;
    setSecondVertIsSet(true);
    return this;
  }

  public void unsetSecondVert() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SECONDVERT_ISSET_ID);
  }

  /** Returns true if field secondVert is set (has been assigned a value) and false otherwise */
  public boolean isSetSecondVert() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SECONDVERT_ISSET_ID);
  }

  public void setSecondVertIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SECONDVERT_ISSET_ID, value);
  }

  public double getPeso() {
    return this.peso;
  }

  public Aresta setPeso(double peso) {
    this.peso = peso;
    setPesoIsSet(true);
    return this;
  }

  public void unsetPeso() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PESO_ISSET_ID);
  }

  /** Returns true if field peso is set (has been assigned a value) and false otherwise */
  public boolean isSetPeso() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PESO_ISSET_ID);
  }

  public void setPesoIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PESO_ISSET_ID, value);
  }

  public boolean isFlag() {
    return this.flag;
  }

  public Aresta setFlag(boolean flag) {
    this.flag = flag;
    setFlagIsSet(true);
    return this;
  }

  public void unsetFlag() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __FLAG_ISSET_ID);
  }

  /** Returns true if field flag is set (has been assigned a value) and false otherwise */
  public boolean isSetFlag() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __FLAG_ISSET_ID);
  }

  public void setFlagIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __FLAG_ISSET_ID, value);
  }

  public java.lang.String getDescricao() {
    return this.descricao;
  }

  public Aresta setDescricao(java.lang.String descricao) {
    this.descricao = descricao;
    return this;
  }

  public void unsetDescricao() {
    this.descricao = null;
  }

  /** Returns true if field descricao is set (has been assigned a value) and false otherwise */
  public boolean isSetDescricao() {
    return this.descricao != null;
  }

  public void setDescricaoIsSet(boolean value) {
    if (!value) {
      this.descricao = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case FIRST_VERT:
      if (value == null) {
        unsetFirstVert();
      } else {
        setFirstVert((java.lang.Integer)value);
      }
      break;

    case SECOND_VERT:
      if (value == null) {
        unsetSecondVert();
      } else {
        setSecondVert((java.lang.Integer)value);
      }
      break;

    case PESO:
      if (value == null) {
        unsetPeso();
      } else {
        setPeso((java.lang.Double)value);
      }
      break;

    case FLAG:
      if (value == null) {
        unsetFlag();
      } else {
        setFlag((java.lang.Boolean)value);
      }
      break;

    case DESCRICAO:
      if (value == null) {
        unsetDescricao();
      } else {
        setDescricao((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case FIRST_VERT:
      return getFirstVert();

    case SECOND_VERT:
      return getSecondVert();

    case PESO:
      return getPeso();

    case FLAG:
      return isFlag();

    case DESCRICAO:
      return getDescricao();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case FIRST_VERT:
      return isSetFirstVert();
    case SECOND_VERT:
      return isSetSecondVert();
    case PESO:
      return isSetPeso();
    case FLAG:
      return isSetFlag();
    case DESCRICAO:
      return isSetDescricao();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof Aresta)
      return this.equals((Aresta)that);
    return false;
  }

  public boolean equals(Aresta that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_firstVert = true;
    boolean that_present_firstVert = true;
    if (this_present_firstVert || that_present_firstVert) {
      if (!(this_present_firstVert && that_present_firstVert))
        return false;
      if (this.firstVert != that.firstVert)
        return false;
    }

    boolean this_present_secondVert = true;
    boolean that_present_secondVert = true;
    if (this_present_secondVert || that_present_secondVert) {
      if (!(this_present_secondVert && that_present_secondVert))
        return false;
      if (this.secondVert != that.secondVert)
        return false;
    }

    boolean this_present_peso = true;
    boolean that_present_peso = true;
    if (this_present_peso || that_present_peso) {
      if (!(this_present_peso && that_present_peso))
        return false;
      if (this.peso != that.peso)
        return false;
    }

    boolean this_present_flag = true;
    boolean that_present_flag = true;
    if (this_present_flag || that_present_flag) {
      if (!(this_present_flag && that_present_flag))
        return false;
      if (this.flag != that.flag)
        return false;
    }

    boolean this_present_descricao = true && this.isSetDescricao();
    boolean that_present_descricao = true && that.isSetDescricao();
    if (this_present_descricao || that_present_descricao) {
      if (!(this_present_descricao && that_present_descricao))
        return false;
      if (!this.descricao.equals(that.descricao))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + firstVert;

    hashCode = hashCode * 8191 + secondVert;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(peso);

    hashCode = hashCode * 8191 + ((flag) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((isSetDescricao()) ? 131071 : 524287);
    if (isSetDescricao())
      hashCode = hashCode * 8191 + descricao.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(Aresta other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetFirstVert()).compareTo(other.isSetFirstVert());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFirstVert()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.firstVert, other.firstVert);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetSecondVert()).compareTo(other.isSetSecondVert());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSecondVert()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.secondVert, other.secondVert);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPeso()).compareTo(other.isSetPeso());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPeso()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.peso, other.peso);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetFlag()).compareTo(other.isSetFlag());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFlag()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.flag, other.flag);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDescricao()).compareTo(other.isSetDescricao());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDescricao()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.descricao, other.descricao);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("Aresta(");
    boolean first = true;

    sb.append("firstVert:");
    sb.append(this.firstVert);
    first = false;
    if (!first) sb.append(", ");
    sb.append("secondVert:");
    sb.append(this.secondVert);
    first = false;
    if (!first) sb.append(", ");
    sb.append("peso:");
    sb.append(this.peso);
    first = false;
    if (!first) sb.append(", ");
    sb.append("flag:");
    sb.append(this.flag);
    first = false;
    if (!first) sb.append(", ");
    sb.append("descricao:");
    if (this.descricao == null) {
      sb.append("null");
    } else {
      sb.append(this.descricao);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ArestaStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ArestaStandardScheme getScheme() {
      return new ArestaStandardScheme();
    }
  }

  private static class ArestaStandardScheme extends org.apache.thrift.scheme.StandardScheme<Aresta> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Aresta struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FIRST_VERT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.firstVert = iprot.readI32();
              struct.setFirstVertIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SECOND_VERT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.secondVert = iprot.readI32();
              struct.setSecondVertIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PESO
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.peso = iprot.readDouble();
              struct.setPesoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // FLAG
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.flag = iprot.readBool();
              struct.setFlagIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // DESCRICAO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.descricao = iprot.readString();
              struct.setDescricaoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Aresta struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(FIRST_VERT_FIELD_DESC);
      oprot.writeI32(struct.firstVert);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(SECOND_VERT_FIELD_DESC);
      oprot.writeI32(struct.secondVert);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PESO_FIELD_DESC);
      oprot.writeDouble(struct.peso);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(FLAG_FIELD_DESC);
      oprot.writeBool(struct.flag);
      oprot.writeFieldEnd();
      if (struct.descricao != null) {
        oprot.writeFieldBegin(DESCRICAO_FIELD_DESC);
        oprot.writeString(struct.descricao);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ArestaTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ArestaTupleScheme getScheme() {
      return new ArestaTupleScheme();
    }
  }

  private static class ArestaTupleScheme extends org.apache.thrift.scheme.TupleScheme<Aresta> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Aresta struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetFirstVert()) {
        optionals.set(0);
      }
      if (struct.isSetSecondVert()) {
        optionals.set(1);
      }
      if (struct.isSetPeso()) {
        optionals.set(2);
      }
      if (struct.isSetFlag()) {
        optionals.set(3);
      }
      if (struct.isSetDescricao()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetFirstVert()) {
        oprot.writeI32(struct.firstVert);
      }
      if (struct.isSetSecondVert()) {
        oprot.writeI32(struct.secondVert);
      }
      if (struct.isSetPeso()) {
        oprot.writeDouble(struct.peso);
      }
      if (struct.isSetFlag()) {
        oprot.writeBool(struct.flag);
      }
      if (struct.isSetDescricao()) {
        oprot.writeString(struct.descricao);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Aresta struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.firstVert = iprot.readI32();
        struct.setFirstVertIsSet(true);
      }
      if (incoming.get(1)) {
        struct.secondVert = iprot.readI32();
        struct.setSecondVertIsSet(true);
      }
      if (incoming.get(2)) {
        struct.peso = iprot.readDouble();
        struct.setPesoIsSet(true);
      }
      if (incoming.get(3)) {
        struct.flag = iprot.readBool();
        struct.setFlagIsSet(true);
      }
      if (incoming.get(4)) {
        struct.descricao = iprot.readString();
        struct.setDescricaoIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

