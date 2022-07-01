package ch.teachu.teachu_admin.shared.schoolconfig;

import org.eclipse.scout.rt.dataobject.enumeration.IEnum;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public enum SchoolConfigCodeType implements IEnum {
  LANGUAGE("language", LanguageCodeType.class);

  private final String value;
  private final Class<? extends AbstractCodeType<String, String>> codeType;

  SchoolConfigCodeType(String value, Class<? extends AbstractCodeType<String, String>> codeType) {
    this.value = value;
    this.codeType = codeType;
  }

  @Override
  public String stringValue() {
    return value;
  }

  public Class<? extends AbstractCodeType<String, String>> getCodeType() {
    return codeType;
  }
}
