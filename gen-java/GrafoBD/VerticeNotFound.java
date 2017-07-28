/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package GrafoBD;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-07-27")
public class VerticeNotFound extends org.apache.thrift.TException implements org.apache.thrift.TBase<VerticeNotFound, VerticeNotFound._Fields>, java.io.Serializable, Cloneable, Comparable<VerticeNotFound> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("VerticeNotFound");

  private static final org.apache.thrift.protocol.TField ERROR_MSG_VERTICE_FIELD_DESC = new org.apache.thrift.protocol.TField("errorMsgVertice", org.apache.thrift.protocol.TType.STRING, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new VerticeNotFoundStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new VerticeNotFoundTupleSchemeFactory();

  public java.lang.String errorMsgVertice; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ERROR_MSG_VERTICE((short)1, "errorMsgVertice");

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
        case 1: // ERROR_MSG_VERTICE
          return ERROR_MSG_VERTICE;
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
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ERROR_MSG_VERTICE, new org.apache.thrift.meta_data.FieldMetaData("errorMsgVertice", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(VerticeNotFound.class, metaDataMap);
  }

  public VerticeNotFound() {
  }

  public VerticeNotFound(
    java.lang.String errorMsgVertice)
  {
    this();
    this.errorMsgVertice = errorMsgVertice;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public VerticeNotFound(VerticeNotFound other) {
    if (other.isSetErrorMsgVertice()) {
      this.errorMsgVertice = other.errorMsgVertice;
    }
  }

  public VerticeNotFound deepCopy() {
    return new VerticeNotFound(this);
  }

  @Override
  public void clear() {
    this.errorMsgVertice = null;
  }

  public java.lang.String getErrorMsgVertice() {
    return this.errorMsgVertice;
  }

  public VerticeNotFound setErrorMsgVertice(java.lang.String errorMsgVertice) {
    this.errorMsgVertice = errorMsgVertice;
    return this;
  }

  public void unsetErrorMsgVertice() {
    this.errorMsgVertice = null;
  }

  /** Returns true if field errorMsgVertice is set (has been assigned a value) and false otherwise */
  public boolean isSetErrorMsgVertice() {
    return this.errorMsgVertice != null;
  }

  public void setErrorMsgVerticeIsSet(boolean value) {
    if (!value) {
      this.errorMsgVertice = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case ERROR_MSG_VERTICE:
      if (value == null) {
        unsetErrorMsgVertice();
      } else {
        setErrorMsgVertice((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ERROR_MSG_VERTICE:
      return getErrorMsgVertice();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case ERROR_MSG_VERTICE:
      return isSetErrorMsgVertice();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof VerticeNotFound)
      return this.equals((VerticeNotFound)that);
    return false;
  }

  public boolean equals(VerticeNotFound that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_errorMsgVertice = true && this.isSetErrorMsgVertice();
    boolean that_present_errorMsgVertice = true && that.isSetErrorMsgVertice();
    if (this_present_errorMsgVertice || that_present_errorMsgVertice) {
      if (!(this_present_errorMsgVertice && that_present_errorMsgVertice))
        return false;
      if (!this.errorMsgVertice.equals(that.errorMsgVertice))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetErrorMsgVertice()) ? 131071 : 524287);
    if (isSetErrorMsgVertice())
      hashCode = hashCode * 8191 + errorMsgVertice.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(VerticeNotFound other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetErrorMsgVertice()).compareTo(other.isSetErrorMsgVertice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetErrorMsgVertice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.errorMsgVertice, other.errorMsgVertice);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("VerticeNotFound(");
    boolean first = true;

    sb.append("errorMsgVertice:");
    if (this.errorMsgVertice == null) {
      sb.append("null");
    } else {
      sb.append(this.errorMsgVertice);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class VerticeNotFoundStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public VerticeNotFoundStandardScheme getScheme() {
      return new VerticeNotFoundStandardScheme();
    }
  }

  private static class VerticeNotFoundStandardScheme extends org.apache.thrift.scheme.StandardScheme<VerticeNotFound> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, VerticeNotFound struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ERROR_MSG_VERTICE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.errorMsgVertice = iprot.readString();
              struct.setErrorMsgVerticeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, VerticeNotFound struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.errorMsgVertice != null) {
        oprot.writeFieldBegin(ERROR_MSG_VERTICE_FIELD_DESC);
        oprot.writeString(struct.errorMsgVertice);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class VerticeNotFoundTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public VerticeNotFoundTupleScheme getScheme() {
      return new VerticeNotFoundTupleScheme();
    }
  }

  private static class VerticeNotFoundTupleScheme extends org.apache.thrift.scheme.TupleScheme<VerticeNotFound> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, VerticeNotFound struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetErrorMsgVertice()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetErrorMsgVertice()) {
        oprot.writeString(struct.errorMsgVertice);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, VerticeNotFound struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.errorMsgVertice = iprot.readString();
        struct.setErrorMsgVerticeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
